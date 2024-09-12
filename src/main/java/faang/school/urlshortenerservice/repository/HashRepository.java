package faang.school.urlshortenerservice.repository;

import faang.school.urlshortenerservice.entity.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashRepository extends JpaRepository<Hash, Long> {
    @Query(nativeQuery = true, value = """
            SELECT nextval('unique_number_seq')
            FROM generate_series(1, ?1)
            """)
    List<Long> getUniqueNumbers(long count);

    @Modifying
    @Query(nativeQuery = true, value = """
            DELETE FROM hash
            WHERE id IN (
                SELECT id FROM hash
                ORDER BY random()
                LIMIT ?1
                )
            RETURNING hash
            """)
    List<String> getHashBatch(int batchSize);
}