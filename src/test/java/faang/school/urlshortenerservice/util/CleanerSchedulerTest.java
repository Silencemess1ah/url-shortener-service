package faang.school.urlshortenerservice.util;

import faang.school.urlshortenerservice.JdbcAwareTest;
import faang.school.urlshortenerservice.repository.HashRepository;
import faang.school.urlshortenerservice.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class CleanerSchedulerTest extends JdbcAwareTest {

    private CleanerScheduler cleanerScheduler;
    private UrlRepository urlRepository;
    private HashRepository hashRepository;

    @BeforeEach
    void setup() {
        super.initJdbcTemplate();
        urlRepository = new UrlRepository(jdbcTemplate);
        hashRepository = new HashRepository(jdbcTemplate, 10);
        cleanerScheduler = new CleanerScheduler(urlRepository, hashRepository);

        jdbcTemplate.execute("DROP TABLE IF EXISTS url");
        jdbcTemplate.execute("CREATE TABLE url (hash VARCHAR(6) PRIMARY KEY, url TEXT NOT NULL, created_at TIMESTAMP)");
        jdbcTemplate.execute("DROP TABLE IF EXISTS hash");
        jdbcTemplate.execute("CREATE TABLE hash (hash VARCHAR(6) PRIMARY KEY)");
    }

    @Test
    void testCleanUpOutdatedUrls() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime twoYearsAgo = now.minusYears(2);

        jdbcTemplate.update("INSERT INTO url (hash, url, created_at) VALUES (?, ?, ?)", "hash1", "http://example1.com", twoYearsAgo);
        jdbcTemplate.update("INSERT INTO url (hash, url, created_at) VALUES (?, ?, ?)", "hash2", "http://example2.com", oneYearAgo.plusDays(1));
        jdbcTemplate.update("INSERT INTO url (hash, url, created_at) VALUES (?, ?, ?)", "hash3", "http://example3.com", now);

        cleanerScheduler.cleanUpOutdatedUrls();

        int remainingUrlCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM url", Integer.class);
        assertEquals(2, remainingUrlCount);

        List<String> remainingHashes = jdbcTemplate.queryForList("SELECT hash FROM url", String.class);
        assertTrue(remainingHashes.contains("hash2"));
        assertTrue(remainingHashes.contains("hash3"));

        List<String> savedHashes = jdbcTemplate.queryForList("SELECT hash FROM hash", String.class);
        assertEquals(1, savedHashes.size());
        assertTrue(savedHashes.contains("hash1"));
    }
}