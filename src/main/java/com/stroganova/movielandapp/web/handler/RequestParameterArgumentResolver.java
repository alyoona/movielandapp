package com.stroganova.movielandapp.web.handler;

import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.request.MovieRequestParameterList;
import com.stroganova.movielandapp.request.SortDirection;
import com.stroganova.movielandapp.request.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class RequestParameterArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PRICE_SORT_ATTRIBUTE = "price";
    private static final String RATING_SORT_ATTRIBUTE = "rating";
    private static final String CURRENCY_SORT_ATTRIBUTE = "currency";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MovieRequestParameterList.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String priceOrderValue = webRequest.getParameter(PRICE_SORT_ATTRIBUTE);

        if (priceOrderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(priceOrderValue);
            return getSortDirectionRequestParameter(PRICE_SORT_ATTRIBUTE, sortOrder);
        }

        String ratingOrderValue = webRequest.getParameter(RATING_SORT_ATTRIBUTE);

        if (ratingOrderValue != null) {
            SortOrder sortOrder = SortOrder.getByName(ratingOrderValue);
            if (SortOrder.ASC.equals(sortOrder)) {
                throw new IllegalArgumentException("Incorrect sort parameters, expected movies sorting by rating (desc)");
            }
            return getSortDirectionRequestParameter(RATING_SORT_ATTRIBUTE, sortOrder);
        }

        String currencyValue = webRequest.getParameter(CURRENCY_SORT_ATTRIBUTE);
        if (currencyValue != null) {
            Currency currency = Currency.getByName(currencyValue);
            return new MovieRequestParameterList(null, currency);
        }
         return null;
    }

    private MovieRequestParameterList getSortDirectionRequestParameter(String sortAttribute, SortOrder orderValue) {
        SortDirection sortDirection = new SortDirection(sortAttribute, orderValue);
        return new MovieRequestParameterList(sortDirection, null);
    }
}
