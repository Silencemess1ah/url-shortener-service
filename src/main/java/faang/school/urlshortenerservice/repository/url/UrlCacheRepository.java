package faang.school.urlshortenerservice.repository.url;

import faang.school.urlshortenerservice.entity.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlCacheRepository extends CrudRepository<Url, String> {

    Optional<Url> findUrlByHash(String hash);
}