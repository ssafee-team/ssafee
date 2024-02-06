package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.config.S3Config;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Config s3Config;
    private final S3Client s3Client;

    public String putImage(String prefix, MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String fileHash = HexFormat.of().formatHex(digest.digest(file.getBytes()));
            String key = prefix + fileHash + ".jpg";

            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(s3Config.bucket())
                            .key(key)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return "https://" + s3Config.publicAccessDomain() + "/" + key;
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new ShopException(ShopErrorCode.FAILED_UPLOAD_IMAGE);
        }
    }

}
