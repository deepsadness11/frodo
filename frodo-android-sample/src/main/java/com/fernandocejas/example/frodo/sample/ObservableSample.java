package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

public class ObservableSample {
  public ObservableSample() {
  }

  @RxLogObservable
  public Observable<Integer> numbers() {
    return Observable.just(1, 2);
  }

  @RxLogObservable
  public Observable<String> names() {
    return Observable.just("Fernando", "Silvia");
  }

  @RxLogObservable
  public Observable<String> error() {
    return Observable.error(new IllegalArgumentException("My error"));
  }

  @RxLogObservable
  public Observable<List<MyDummyClass>> list() {
    return Observable.just(buildDummyList());
  }

  /**
   * Nothing should happen here when annotating this method with {@link RxLogObservable}
   * because it does not returns an {@link Observable}.
   */
  @RxLogObservable
  public List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
  }

  public Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  public Observable<String> stringsWithError() {
    return Observable.error(new IllegalArgumentException("My Subscriber error"));
  }

  public Observable<Integer> numbersBackpressure() {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        try {
          if (!subscriber.isUnsubscribed()) {
            for (int i = 1; i < 10000; i++) {
              subscriber.onNext(i);
            }
            subscriber.onCompleted();
          }
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public static final class MyDummyClass {
    private final String name;

    MyDummyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}