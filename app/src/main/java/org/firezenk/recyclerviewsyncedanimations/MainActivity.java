package org.firezenk.recyclerviewsyncedanimations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.my_recyclerview) RecyclerView myList;

    private PublishSubject<Long> animationTimer;
    private Subscription animationSubscription;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        animationSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(animationTimer = PublishSubject.create());

        RendererBuilder<Item> rendererBuilder = new RendererBuilder<>(new CustomRenderer(animationTimer));
        RVRendererAdapter adapter = new RVRendererAdapter<>(rendererBuilder);
        myList.setAdapter(adapter);

        List<Item> items = new ArrayList<>();

        items.add(new Item("Madrid", 18, 10));
        items.add(new Item("California", 10, 10));
        items.add(new Item("Moscú", 20, 10));
        items.add(new Item("Taiwan", 23, 10));
        items.add(new Item("Tenerife", 17, 10));
        items.add(new Item("Madrid", 18, 10));
        items.add(new Item("California", 10, 10));
        items.add(new Item("Moscú", 20, 10));
        items.add(new Item("Taiwan", 23, 10));
        items.add(new Item("Tenerife", 17, 10));
        items.add(new Item("Madrid", 18, 10));
        items.add(new Item("California", 10, 10));
        items.add(new Item("Moscú", 20, 10));
        items.add(new Item("Taiwan", 23, 10));
        items.add(new Item("Tenerife", 17, 10));

        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (!animationSubscription.isUnsubscribed()) {
            animationSubscription.unsubscribe();
        }
    }
}
