package com.demo.cooking.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shady Shalaby on 22/05/2017.
 */

public class NetworkManager {

    private static NetworkManager instance = new NetworkManager();
    private static String TAG = NetworkManager.class.getName();

    private static String BASE_URL = "https://cookmenu4all.000webhostapp.com";
    private static final String USER_LOGIN = "/userLogin";
    private static final String USER_SIGN_UP = "/userSignup";
    private static final String GET_ALL_CATEGORIES = "/getcategories";
    private static final String GET_SUBCATEGORIES = "/getsubcategories";
    private static final String GET_SUBCATEGORY_FOOD = "/getfoodinsubcat";
    private static final String GET_FOOD_DETAILS = "/getfooddetails";

    private NetworkManager() {

    }

    public static synchronized NetworkManager getInstance() {
        return instance;
    }

    public void makeRequest(Context context, String url, String methodType, FutureCallback<Response<String>> futureCallback) {
        Log.i(TAG, "URL " + url);

        Ion.with(context)
                .load(methodType, url)
                .asString()
                .withResponse()
                .setCallback(futureCallback);
    }

    public void makeRequest(Context context, String url, String methodType, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        Log.i(TAG, "URL " + url);
        Log.i(TAG, "params " + params.toString());

        Ion.with(context)
                .load(methodType, url)
                .setBodyParameters(params)
                .asString()
                .withResponse()
                .setCallback(futureCallback);
    }

    /****************************
     * Web Services Builder Functions
     ****************************/

    public void userLogin(Context context, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + USER_LOGIN, "POST", params, futureCallback);
    }

    public void userSignUp(Context context, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + USER_SIGN_UP, "POST", params, futureCallback);
    }

    public void getCategories(Context context, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + GET_ALL_CATEGORIES, "POST", futureCallback);
    }

    public void getSubCategories(Context context, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + GET_SUBCATEGORIES, "POST", params, futureCallback);
    }

    public void getSubCategoryMeals(Context context, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + GET_SUBCATEGORY_FOOD, "POST", params, futureCallback);
    }

    public void getFoodDetails(Context context, Map<String, List<String>> params, FutureCallback<Response<String>> futureCallback) {
        makeRequest(context, BASE_URL + GET_FOOD_DETAILS, "POST", params, futureCallback);
    }

    /**************************** Parameter Builder Functions ****************************/

    /**
     * Build params for USER_LOGIN web service
     *
     * @param email
     * @param password
     * @return
     */
    @NonNull
    public static HashMap<String, List<String>> buildUserLoginParams(final String email, final String password) {
        return new HashMap<String, List<String>>() {
            {
                put("em", new ArrayList<String>() {{
                    add(email);
                }});
                put("pw", new ArrayList<String>() {{
                    add(password);
                }});
            }
        };
    }

    /**
     * Build params for USER_SIGN_UP web service
     *
     * @param userName
     * @param email
     * @param password
     * @return
     */
    @NonNull
    public static HashMap<String, List<String>> buildUserSignUpParams(final String userName, final String email, final String password) {
        return new HashMap<String, List<String>>() {
            {
                put("un", new ArrayList<String>() {{
                    add(email);
                }});
                put("em", new ArrayList<String>() {{
                    add(email);
                }});
                put("pw", new ArrayList<String>() {{
                    add(password);
                }});
            }
        };
    }

    /**
     * Build params for GET_SUBCATEGORIES web service of certain category
     *
     * @param catId
     * @return Map
     */
    @NonNull
    public static HashMap<String, List<String>> buildSubCategoryParams(final int catId) {
        return new HashMap<String, List<String>>() {
            {
                put("categoryid", new ArrayList<String>() {{
                    add(catId + "");
                }});
            }
        };
    }

    /**
     * Build params for GET_SUBCATEGORIES web service of certain subcategory
     *
     * @param subCatId
     * @return Map
     */
    @NonNull
    public static HashMap<String, List<String>> buildMealsParams(final int subCatId) {
        return new HashMap<String, List<String>>() {
            {
                put("subcatid", new ArrayList<String>() {{
                    add(subCatId + "");
                }});
            }
        };
    }

    /**
     * Build params for GET_SUBCATEGORIES web service of certain subcategory
     *
     * @param foodId
     * @return Map
     */
    @NonNull
    public static HashMap<String, List<String>> buildFoodDescriptionParams(final int foodId) {
        return new HashMap<String, List<String>>() {
            {
                put("foodid", new ArrayList<String>() {{
                    add(foodId + "");
                }});
            }
        };
    }


}
