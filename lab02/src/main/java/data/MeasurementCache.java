package data;

import model.DataModel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.WeakHashMap;

public class MeasurementCache {
    private final WeakHashMap<String, WeakReference<List<DataModel>>> cache = new WeakHashMap<>();

    public List<DataModel> getCachedData(String filePath) {
        WeakReference<List<DataModel>> ref = cache.get(filePath);
        return (ref != null) ? ref.get() : null;
    }

    public void cacheData(String filePath, List<DataModel> data) {
        cache.put(filePath, new WeakReference<>(data));
    }
}
