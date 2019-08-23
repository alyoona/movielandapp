package com.stroganova.movielandapp.web.handler;

import com.stroganova.movielandapp.request.RequestParameter;
import com.stroganova.movielandapp.request.SortDirection;
import com.stroganova.movielandapp.request.SortOrder;
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
        return parameter.getParameterType().equals(RequestParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String priceOrderValue = webRequest.getParameter(PRICE_SORT_ATTRIBUTE);

        if (priceOrderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(priceOrderValue);
            return getRequestParameter(PRICE_SORT_ATTRIBUTE, sortOrder);
        }

        String ratingOrderValue = webRequest.getParameter(RATING_SORT_ATTRIBUTE);

        if (ratingOrderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(ratingOrderValue);
            if (SortOrder.ASC.equals(sortOrder)) {
                throw new IllegalArgumentException("Incorrect sort parameters, expected movies sorting by rating (desc)");
            }
            return getRequestParameter(RATING_SORT_ATTRIBUTE, sortOrder);
        }

         return null;
    }

    private RequestParameter getRequestParameter(String sortAttribute, SortOrder orderValue) {
        SortDirection sortDirection = new SortDirection(sortAttribute, orderValue);
        return new RequestParameter(sortDirection);
    }
}
