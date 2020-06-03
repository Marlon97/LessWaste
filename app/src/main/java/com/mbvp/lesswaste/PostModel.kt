package com.mbvp.lesswaste

data class PostModel(var caption:String? = null,
                var creationDate:Long? = null,
                var imageUrl:String? = null,
                var likes:Int? = null,
                var ownerUid:String? = null,
                var ingredientes:String? = null,
                var linkYT:String? = null,
                var etiquetas:String? = null,
                var instrucciones:String? = null) {

    companion object {
        val arrPostModel = arrayOf(PostModel("Caption", 1591205765, "https://firebasestorage.googleapis.com/v0/b/lesswaste-28106.appspot.com/o/post_images%2F611F5FD9-805C-46B7-A685-545B05E81B49?alt=media&token=4484f0c8-46de-4591-84cc-ff9791b01935",0,"ownerId","ingredientes","linkYT","#etiquetas","instrucciones"))
    }
}