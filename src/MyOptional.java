import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MyOptional<T> {
    private T object;


    private MyOptional(T object) {
        this.object = object;
    }

    public MyOptional() {
        this.object = null;
    }

    public static <T> MyOptional<T> empty() {
        return new MyOptional<T>();
    }

    public static <T> MyOptional<T> ofNullable(T object) {
        if (object == null) {
            return empty();
        } else return of(object);

    }

    private static <T> MyOptional<T> of(T object) {
        return new MyOptional<>(object);
    }

    public T getObject() {
        if (object == null) throw new NoSuchElementException("Null");
        return object;
    }

    private boolean isPresent() {
        if (object == null) return false;
        return true;
    }

    public T orElse(T otherObject) {
        if (object == null) {
            return otherObject;
        } else return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof MyOptional) return false;
        MyOptional<?> other = (MyOptional<?>) o;
        return Objects.equals(object, other.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (object != null) consumer.accept(object);
    }

    public T orElseGet(Supplier<? extends T> other) {
        if (object == null) {
            return other.get();
        } else return object;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (object != null) {
            return object;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public MyOptional<T> filter(Predicate<? super T> predicate) {
        if (predicate == null) throw new NullPointerException();

        if (!isPresent()) return this;
        else if (predicate.test(object)) return this;
        else return empty();
    }
    public<U> MyOptional<U> map(Function<? super T, ? extends U> mapper) {
        if (mapper == null) throw new NullPointerException();
        if (!isPresent())
            return empty();
        else {
            return MyOptional.ofNullable(mapper.apply(object));
        }
    }
    public<U> MyOptional<U> flatMap(Function<? super T, MyOptional<U>> mapper) {
        if (mapper == null) throw new NullPointerException();
        if (!isPresent())
            return empty();
        else {
            if (mapper.apply(object) == null)  throw new NullPointerException();
            return (MyOptional<U>) object;
        }
    }


}
