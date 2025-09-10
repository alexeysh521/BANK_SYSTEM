package freelance.project.bank_system.service;

import freelance.project.bank_system.dto.AddDataRequest;
import freelance.project.bank_system.dto.ViewDataUserResponse;
import freelance.project.bank_system.enums.AccountStatusType;
import freelance.project.bank_system.enums.UserStatusType;
import freelance.project.bank_system.model.Data;
import freelance.project.bank_system.model.User;
import freelance.project.bank_system.repository.DataRepository;
import freelance.project.bank_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
    private final UserRepository userRepository;

    @Transactional
    public String create(AddDataRequest dto, User userR){
        User user = userRepository.findUserById(userR.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Data data = new Data(
                dto.firstName(),
                dto.averageName(),
                dto.lastName(),
                dto.email(),
                dto.passportSeriesAndNumber(),
                dto.dateOfBirth(),
                user
        );

        user.setStatus(UserStatusType.ACTIVE);

        user.getAccounts().stream()
            .filter(ac -> ac.getStatus() == AccountStatusType.PENDING_VERIFICATION)
            .forEach(ac -> ac.setStatus(AccountStatusType.ACTIVE));

        user.setData(data);
        dataRepository.save(data);

        return "Saved successfully";
    }

    public ViewDataUserResponse viewDataUserById(UUID user_id){
        User user = userRepository.findUserById(user_id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getData() == null)
            throw new EntityNotFoundException("The user did not enter his data");

        Data data = user.getData();

        return new ViewDataUserResponse(
                data.getFirstName(),
                data.getAverageName(),
                data.getLastName(),
                data.getEmail(),
                data.getPassportSeriesAndNumber(),
                data.getDateOfBirth()
        );
    }

}
