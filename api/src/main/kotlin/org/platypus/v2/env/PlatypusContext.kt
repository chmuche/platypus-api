package org.platypus.v2.env

interface PlatypusContext {
    companion object {
        fun newContext():PlatypusContext{
            return PlatypusContextImp()
        }
    }

}

class PlatypusContextImp : PlatypusContext
