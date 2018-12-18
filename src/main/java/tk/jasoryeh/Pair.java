package tk.jasoryeh;

import lombok.Getter;
import lombok.Setter;

public class Pair<K, V> {
    @Getter
    @Setter
    private K key;
    @Getter
    @Setter
    private V value;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<K, V>(key, value);
    }
}
