package it.gabrieletondi.journey.expecteverything;

import java.time.LocalDateTime;

public class SocialNetwork {
    private final UserRepository userRepository;
    private final MessageValidator messageValidator;
    private final Clock clock;
    private final PostRepository postRepository;

    public SocialNetwork(UserRepository userRepository, MessageValidator messageValidator, Clock clock, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.messageValidator = messageValidator;
        this.clock = clock;
        this.postRepository = postRepository;
    }

    public void postUpdate(String username, String message) {
        User user = userRepository.findByUsername(username);
        LocalDateTime time = clock.currentTime();

        if (!messageValidator.isValid(message)) {
            throw new InvalidMessageException();
        }

        postRepository.save(new Post(user, message, time));
    }
}
