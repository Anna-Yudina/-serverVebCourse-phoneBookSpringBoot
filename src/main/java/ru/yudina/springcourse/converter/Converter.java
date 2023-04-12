package ru.yudina.springcourse.converter;

import java.util.List;

public interface Converter<S, D> {
    D convert(S source);

    List<D> convert(List<S> source);
}
