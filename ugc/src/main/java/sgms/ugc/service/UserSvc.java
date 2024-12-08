package sgms.ugc.service;

import sgms.ugc.model.User;
import sgms.ugc.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UserSvc {
    private final UserRepo userRepo;

    public UserSvc(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean add(User u){
        try{
            userRepo.save(u);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(long id){
        try{
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public long passwordLogin(String identifier, String password) {
        try {
            User user = userRepo.findByTel(identifier);
            if (user == null) {
                user = userRepo.findByEmail(identifier);
            }
            if (user == null) {
                return -1;
            }

            String hashedPassword = DigestUtils.sha256Hex(password);
            if (hashedPassword.equals(user.getPassword())) {
                return user.getId();
            }
            return -2;

        } catch (Exception e) {
            return -3;
        }
    }
}
