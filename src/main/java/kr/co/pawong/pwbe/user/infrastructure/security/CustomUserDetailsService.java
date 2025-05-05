package kr.co.pawong.pwbe.user.infrastructure.security;

import kr.co.pawong.pwbe.user.infrastructure.repository.UserJpaRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;


    public CustomUserDetailsService(
        UserJpaRepository userJpaRepository) {
      this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userJpaRepository.findBySocialId(Long.valueOf(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(userEntity);
    }

}
