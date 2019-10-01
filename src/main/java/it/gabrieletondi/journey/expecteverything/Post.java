package it.gabrieletondi.journey.expecteverything;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private final User user;
    private final String message;
    private final LocalDateTime time;

    public Post(User user, String message, LocalDateTime time) {
        this.user = user;
        this.message = message;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(user, post.user) &&
                Objects.equals(message, post.message) &&
                Objects.equals(time, post.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, time);
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
