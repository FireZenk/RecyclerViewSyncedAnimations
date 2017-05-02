package org.firezenk.recyclerviewsyncedanimations;

import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import rx.subjects.PublishSubject;

class CustomRendererBuilder extends RendererBuilder<Item> {

    CustomRendererBuilder(PublishSubject<Long> animationTimer) {
        ListAdapteeCollection<Renderer<? extends Item>> prototypes = new ListAdapteeCollection<>();
        prototypes.add(new CustomRenderer(animationTimer));
        setPrototypes(prototypes);
    }

    @Override protected Class getPrototypeClass(Item item) {
        return CustomRenderer.class;
    }
}
