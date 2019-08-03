package com.mike4christ.storexapp.retrofit_interface;



/*
* Hi! here is my API Client Class where all
* endpoint are being called
*
*
*
*
*
* */

import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.models.customer.AppEntryModel.FbTokenData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.LoginGetData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.LoginPostData;
import com.mike4christ.storexapp.models.customer.CartModels.AddtoCart;
import com.mike4christ.storexapp.models.customer.CartModels.CartId;
import com.mike4christ.storexapp.models.customer.CartModels.AddCartResponse;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.CartModels.GetTotalAmount;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegion;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegionDetail;
import com.mike4christ.storexapp.models.customer.OrderModel.CreateOrder;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderAddress;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderId;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderList;
import com.mike4christ.storexapp.models.customer.OrderModel.Order_Detail;
import com.mike4christ.storexapp.models.customer.OrderModel.PutOrderAddress;
import com.mike4christ.storexapp.models.customer.Product.ProductModel_Obj;
import com.mike4christ.storexapp.models.customer.Product.ProductDetailModel;
import com.mike4christ.storexapp.models.customer.AppEntryModel.RegGetData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.RegPostData;
import com.mike4christ.storexapp.models.customer.StripePayment.SendpaymentData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("customers")
    Call<RegGetData> register(@Body RegPostData regPostData);

    @POST("customers/login")
    Call<LoginGetData> login(@Body LoginPostData loginPostData);

    @POST("customers/facebook")
    Call<RegGetData> fblogin(@Body FbTokenData fbTokenData);

    @GET("products/inCategory/{category_id}?page=1&limit=50&description_length=300")
    Call<ProductModel_Obj> product(@Path("category_id") int category_id);

    @GET("products/{product_id}/details")
    Call<List<ProductDetailModel>> product_detail( @Path("product_id") int product_id);

    @GET("shoppingcart/generateUniqueId")
    Call<CartId> cartUniquieId();

    @GET("attributes/values/{attribute_id}")
    Call<List<Attribute>> attribute(@Path("attribute_id") int attribute_id);

    @POST("shoppingcart/add")
    Call<List<AddCartResponse>> addCartResponse(@Body AddtoCart addtoCart);

    @GET("shoppingcart/{cart_id}")
    Call<List<CartList>> cartlist(@Path("cart_id") String cart_id);

    @PUT("shoppingcart/update/{item_id}")
    Call<List<CartList>> cartedit(@Path("item_id") int item_id, @Field("quantity") int quantity);

    @GET("shoppingcart/totalAmount/{cart_id}")
    Call<GetTotalAmount> cart_total_amt(@Path("cart_id") String cart_id);

    @DELETE("shoppingcart/empty/{cart_id}")
    Call<List<CartList>> empty_cart(@Path("cart_id") String cart_id);

    @GET("shipping/regions")
    Call<List<ShippingRegion>> regions();

    @GET("shipping/regions/{shipping_region_id}")
    Call<List<ShippingRegionDetail>> shipping_detail(@Path("shipping_region_id") int shipping_region_id);

    @PUT("customers/address")
    Call<GetOrderAddress> putOrder_addr(@Header("USER-KEY") String value, PutOrderAddress putOrderAddress);

    @POST("stripe/charge")
    Call<ResponseBody> stripe_response(SendpaymentData sendpaymentData);

    @POST("orders")
    Call<GetOrderId> order_id(CreateOrder createOrder);

    @GET("orders/inCustomer")
    Call<List<GetOrderList>> getOrder_info(@Header("USER-KEY") String value);

    @GET("orders/{order_id}")
    Call<List<Order_Detail>> getOrder_detail(@Header("USER-KEY") String value,@Path("order_id") int order_id);




}
