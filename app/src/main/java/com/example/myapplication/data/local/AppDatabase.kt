package com.example.myapplication.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Jogo::class, ProgressoJogo::class, Tentativa::class, Usuario::class],
    version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun jogoDAO(): JogoDAO
    abstract fun progressoAO(): ProgressoJogoDAO
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun tentativaDAO(): TentativaDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return  tempInstance
            }else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration(true)
                        .build()

                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}