package kr.co.pawong.pwbe.adoption.application.service.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Component
public class ParallelExecutor {
    private final Executor executor;

    public ParallelExecutor(@Qualifier("adoptionExecutor") Executor executor) {
        this.executor = executor;
    }

    /**
     * inputs 의 각 요소에 fn.apply 를 병렬로 수행하고,
     * 모든 작업이 끝난 뒤 결과 리스트를 돌려줍니다.
     */
    public <T, R> List<R> run(List<T> inputs, Function<T, R> fn) {
        // 각 입력마다 CompletableFuture 생성
        List<CompletableFuture<R>> futures = inputs.stream()
                .map(t -> CompletableFuture.supplyAsync(() -> fn.apply(t), executor))
                .toList();

        // 모든 Future 가 완료될 때까지 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 결과 수집
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
