package baydevgroup.net.supportmapfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        /**
         *  Вот здесь ключевое, что мы получаем инстанс не просто android.support.v4.app.Fragment
         *  а com.google.android.gms.maps.SupportMapFragment
         *  потому и кастим, когда отыскиваем по id
         *
         *  внизу же, кастинг не нужен, так как у нас класс MyMapFragment
         *  и так extend'иться от SupportMapFragment
         */
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.fragmentContainer);

            if(fragment == null) {
                fragment =  new MyMapFragment();
                fm.beginTransaction()
                        .add(R.id.fragmentContainer, fragment)
                        .commit();

                /**
                 *  Google обязывает юзать именно асинхронный вызов карты
                 *   "A GoogleMap must be acquired using getMapAsync(OnMapReadyCallback)."
                 *   ссылка: https://developers.google.com/android/reference/com/google/android/gms/maps/MapFragment
                 *
                 *   Чтобы наша активити стала слушателем callback слушателем этого вызова
                 *   нужно заимплементить OnMapReadyCallback и библиотеку
                 *   import com.google.android.gms.maps.OnMapReadyCallback
                 */
                fragment.getMapAsync(this);
            }
    }
    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(52, 34)).title("Marker"));
    }

}
