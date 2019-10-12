package it.gabrieletondi.journey.toomanycollaborators;

import java.util.Objects;

public class Comment {
    private final User user;
    private final String message;

    public Comment(User user, String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return user.equals(comment.user) &&
                message.equals(comment.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
