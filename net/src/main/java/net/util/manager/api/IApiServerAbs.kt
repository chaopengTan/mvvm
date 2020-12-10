package net.util.manage.api

abstract class IApiServerAbs<T> {
    abstract fun getServer():Class<T>
}