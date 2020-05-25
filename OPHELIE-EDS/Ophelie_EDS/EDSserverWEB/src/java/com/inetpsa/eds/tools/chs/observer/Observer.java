/*
 * Creation : 19/08/2015
 */
package com.inetpsa.eds.tools.chs.observer;

public interface Observer<E> {

    void update(Observable<E> o, E updated);
}
