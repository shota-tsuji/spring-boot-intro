package spring.boot.hello.world.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import spring.boot.hello.world.repository.SiteUserRepository;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    private final SiteUserRepository userRepository;

    public UniqueLoginValidator() {
        this.userRepository = null;
    }

    @Autowired
    public UniqueLoginValidator(SiteUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * チェックエラーがある場合falseを返す
     * @param value ユーザ名
     * @param context ConstraintValidatorContextのインスタンス
     * @return ログインに使用するユーザ名が既に存在しないかどうか
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository == null ||
                userRepository.findByUsername(value) == null;
    }
}
