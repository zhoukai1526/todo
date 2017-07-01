package com.iwintrue.todoapplication.annotation;

import android.app.Activity;
import android.view.View;

import com.iwintrue.commonlibrary.utils.T;

import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhoukai on 2017/6/27.
 */

public class ViewUtils {









    public static void injectClick(Activity activity)  {
        //获取当前class
        Class a = activity.getClass();
        //获取说有的方法
        Method[]methods = a.getMethods();
        //遍历成员变量
        for(Method method:methods){
            // 如果指定类型的注释存在于此元素上，则返回 true，否则返回 false。
            if(method.isAnnotationPresent(OnClick3.class)){
                OnClick3 click3 = method.getAnnotation(OnClick3.class);
                //获取注解的值
                int[] ids = click3.value();
                for(int id :ids){
                    View view = activity.findViewById(id);
                    Class v = view.getClass();
                    try {
                        Method method1 = v.getMethod("setOnClicklistener",int.class);
                        method1.setAccessible(true);
                        method1.invoke(view,id);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }
        }


    }






    public  static  void injectContent2(Activity activity){
        Class a  = activity.getClass();
        if(a.isAnnotationPresent(Content2.class)){
            try {
                Content2 content2 = (Content2) a.getAnnotation(Content2.class);
                int id = content2.value();
                Method method = a.getMethod("setContentView",int.class);
                method.setAccessible(true);
                method.invoke(activity,id);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }




    public static void injectContent(Activity activity){
        Class a = activity.getClass();
        //判断是否被当前类注解
        if(a.isAnnotationPresent(Content.class)){
            //获取content中的值
            Content content = (Content) a.getAnnotation(Content.class);
            int layoutId = content.value();
            try {
                Method method = a.getMethod("setContentView",int.class);
                method.setAccessible(true);
                //第一个参数是调用此方法的对象，第二个是调用的参数
                method.invoke(activity,layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public  static void injectContentView(Activity activity){
        Class a = activity.getClass();
        if(a.isAnnotationPresent(ContentView.class)){
            ContentView contentView = (ContentView) a.getAnnotation(ContentView.class);
            int layoutId = contentView.value();
            try {
                Method method = a.getMethod("setContentView",int.class);
                method.setAccessible(true);
                method.invoke(activity,layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public  static void injectViews(Activity activity) {
        Class a = activity.getClass();
        //获取所有的成员变量
        Field[]files = a.getFields();
        for(Field field :files){
            if(field.isAnnotationPresent(ViewInJect.class)){
                ViewInject viewInject = (ViewInject) field.getAnnotation(ViewInJect.class);
                int viewId = viewInject.value();
                try {
                    Method method = a.getMethod("findViewById",int.class);
                    method.setAccessible(true);
                    Object resView = method.invoke(activity,viewId);
                    field.setAccessible(true);
                    field.set(activity,resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void injectEvents2(final Activity activity){
        Class a = activity.getClass();
        //获取方法
        Method[] methods = a.getMethods();
        for(final Method method:methods){
            if(method.isAnnotationPresent(OnClick.class)){
                //获取注解的内容
                OnClick click = method.getAnnotation(OnClick.class);
                int ids[] = click.value();
                for(int id :ids){
                    final View view = activity.findViewById(id);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                method.setAccessible(true);
                                method.invoke(activity,v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        }
    }

    static View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            T.showShort(v.getContext(),"aaaaaaaaaaaaaaa");
        }
    };

    public static void injectEvents(final Activity activity) {
        Class a = activity.getClass();
        //获取所有方法
        Method[]methods= a.getMethods();
        for(Method method :methods)
        {
            if(method.isAnnotationPresent(OnClick.class)){
                OnClick onClick = method.getAnnotation(OnClick.class);
                int[] viewIds = onClick.value();

                EventBase eventBase = onClick.annotationType().getAnnotation(EventBase.class);
                //listenerSetr = setOnClickListener;
                String listenerSetter = eventBase.listenerSetter();
                //listenerType= View.OnclickListener
                Class<?> listenerType = eventBase.listenerType();
                String methodName = eventBase.methodName();
                //使用动态代理
                DynamicHandler handler = new DynamicHandler(activity);
                //动态创建一个包含DynamicHandler成员变量的代理类  创建了View.OnClickListener的实例 listenner实际是一个View.onClickListener接口的对象
//                Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class<?>[]{listenerType},handler);
                //添加click方法
                handler.addMethod(methodName,method);
                for(int viewId:viewIds){
                    try {
                        Method findViewById = a.getMethod("findViewById",int.class);
                        View view = (View) findViewById.invoke(activity,viewId);
                        //获取view的setOnclickListener方法
                        Method setEventListernerMethod = view.getClass().getMethod(listenerSetter,listenerType);
                        setEventListernerMethod.setAccessible(true);

//                        view.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        });
                        //如果是接口类型的参数，则会调用接口的方法
//                        setEventListernerMethod.invoke(view,listener);
                        setEventListernerMethod.invoke(view,listener);
//                        View.OnClickListener listener = new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                T.showShort(activity,"点击了aaa");
//                            }
//                        };
//                        setEventListernerMethod.invoke(view,listener);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public static void injectView2(Activity activity){
        Class a  = activity.getClass();
        //获取所有的成员变量
        Field[] fields = a.getFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(Inject.class)){
                Inject inject = field.getAnnotation(Inject.class);
                //获取到注册的id
                int id = inject.value();
                try {
                    Method method = a.getMethod("findViewById",int.class);
                    method.setAccessible(true);
                    //获取到view
                    Object resView = method.invoke(activity,id);
                    field.setAccessible(true);
                    field.set(activity,resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
