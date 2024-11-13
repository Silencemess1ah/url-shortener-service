package faang.school.urlshortenerservice.service.url;

import faang.school.urlshortenerservice.config.properties.GeneralProperties;
import faang.school.urlshortenerservice.exception.ResourceNotFoundException;
import faang.school.urlshortenerservice.model.url.Url;
import faang.school.urlshortenerservice.repository.postgres.url.UrlRepository;
import faang.school.urlshortenerservice.repository.redis.UrlCacheRepository;
import faang.school.urlshortenerservice.util.HashCache;
import faang.school.urlshortenerservice.validator.AppUrlValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UrlService {
    private static final String GET_URL_PATH = "/url/";

    private final AppUrlValidator appUrlValidator;
    private final HashCache hashCache;
    private final UrlRepository urlRepository;
    private final UrlCacheRepository urlCacheRepository;
    private final GeneralProperties generalProperties;

    public String generateShortUrl(String longUrl) {
        appUrlValidator.validate(longUrl);

        String hash = hashCache.getHash();

        urlRepository.save(build(hash, longUrl));
        urlCacheRepository.save(hash, longUrl);

        return generalProperties.getAppUrl() + GET_URL_PATH + hash;
    }

    public String getUrlByHash(String hash) {
        String url = urlCacheRepository.get(hash);
        if (url == null) {
            url = urlRepository.findByHash(hash)
                    .map(Url::getUrl)
                    .orElseThrow(() -> new ResourceNotFoundException("Url", hash));
        }
        return url;
    }

    public Url build(String hash, String url) {
        return Url.builder()
                .hash(hash)
                .url(url)
                .build();
    }
}
