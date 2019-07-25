package com.stroganova.movielandapp.web.handler;

import com.stroganova.movielandapp.web.entity.SortDirection;
import com.stroganova.movielandapp.web.entity.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SortDirectionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PRICE_SORT_ATTRIBUTE = "price";
    private static final String RATING_SORT_ATTRIBUTE = "rating";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SortDirection.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String orderValue = webRequest.getParameter(PRICE_SORT_ATTRIBUTE);

        if (orderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(orderValue);
            return getSortDirection(PRICE_SORT_ATTRIBUTE, sortOrder.getName());
        }

        orderValue = webRequest.getParameter(RATING_SORT_ATTRIBUTE);

        if (orderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(orderValue);
            if (SortOrder.ASC.equals(sortOrder)) {
                throw new IllegalArgumentException("Incorrect sort parameters, expected movies sorting by rating (desc)");
            }
            return getSortDirection(RATING_SORT_ATTRIBUTE, sortOrder.getName());
        }

         return null;
    }

    private SortDirection getSortDirection(String sortAttribute, String orderValue) {
        SortDirection sortDirection = new SortDirection();
        sortDirection.setField(sortAttribute);
        sortDirection.setOrderValue(orderValue);
        return sortDirection;
    }
}
