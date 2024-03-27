package faang.school.urlshortenerservice.service;

import faang.school.urlshortenerservice.entity.Hash;
import faang.school.urlshortenerservice.repository.HashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashGenerator {
    private final HashRepository hashRepository;
    private final Base62Encoder encoder;
    @Value("${hash.batchSize:1000}")
    private int batchSize;

    @Transactional
    @Async("generateBatchThreadPool")
    public void generateBatchAsync() {
        generateBatch();
    }

    @Transactional
    public List<Hash> getHashes (int amount) {
        List<Hash> hashes = hashRepository.findAndDelete(amount);
        while (hashes.size() < amount) {
            generateBatch();
            hashes.addAll(hashRepository.findAndDelete(amount - hashes.size()));
        }
        return hashes;
    }

    private void generateBatch() {
        List<Hash> hashes = hashRepository.getNextBatch(batchSize)
                .stream()
                .map(encoder::encode)
                .map(Hash::new)
                .toList();

        hashRepository.saveAll(hashes);
    }
}
