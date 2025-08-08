package EatPic.spring.global.aws.s3;

import EatPic.spring.domain.uuid.entity.Uuid;
import EatPic.spring.domain.uuid.repository.UuidRepository;
import EatPic.spring.global.config.AmazonConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;

    public String uploadFile(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    // 해당 이미지가 어떤 디렉토리의 어떤 식별자인지 - userProfile
    public String generateUserProfileKeyName(Uuid uuid) {
        return amazonConfig.getUserProfilePath() + '/' + uuid.getUuid();
    }

    // 해당 이미지가 어떤 디렉토리의 어떤 식별자인지 - newcard
    public String generateNewcardKeyName(Uuid uuid) {
        return amazonConfig.getNewcardPath() + '/' + uuid.getUuid();
    }
}