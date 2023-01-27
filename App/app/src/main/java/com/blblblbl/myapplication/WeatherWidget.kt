package com.blblblbl.myapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.blblblbl.myapplication.data.persistent_storage.PersistentStorage
import com.blblblbl.myapplication.data.persistent_storage.utils.StorageConverter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.weather_widget)
    views.setTextViewText(R.id.appwidget_text_city, "Novosibirsk")
    views.setTextViewText(R.id.appwidget_text_temperature, "15oC")
    views.setTextViewText(R.id.appwidget_condition_text, "cloudy")
    views.setTextViewText(R.id.appwidget_condition_text, "updated")
    val persistentStorage = PersistentStorage(context)
    val forecastJson = persistentStorage.getProperty(PersistentStorage.CURRENT_WEATHER)
    forecastJson?.let { forecastJson->
        val forecast = StorageConverter.forecastInfoFromJson(forecastJson)
        forecast?.let { forecast->
            forecast.location?.name?.let {name->
                views.setTextViewText(R.id.appwidget_text_city, name)
            }
            forecast.current?.tempC?.let { temp->
                views.setTextViewText(R.id.appwidget_text_temperature, "${temp}°C")
            }
            forecast.current?.condition?.text?.let{condition->
                views.setTextViewText(R.id.appwidget_condition_text,"${condition}")
            }
            forecast.current?.condition?.icon?.let{icon->
                val bitmap = Glide.with(context).asBitmap().load("https:$icon")
                Glide.with(context)
                    .asBitmap()
                    .load("https:$icon")
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            views.setImageViewBitmap(R.id.appwidget_condition_icon,resource)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
                //views.setImageViewBitmap(R.id.appwidget_condition_icon,Glide.with(context).asBitmap().load("https:$icon"))
            }
            forecast.current?.lastUpdated?.let{updated->
                views.setTextViewText(R.id.appwidget_updated_text,"${context.resources.getString(R.string.last_updated)}:${updated}")
            }
        }
    }


    //
    val launchMain = Intent(context, MainActivity::class.java)
    val pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0)
    views.setOnClickPendingIntent(R.id.weather_widget,pendingMainIntent)
    //
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}