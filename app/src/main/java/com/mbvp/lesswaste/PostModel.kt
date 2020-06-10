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
        val arrPostModel = arrayOf(PostModel("Caption", 1591205765, "https://firebasestorage.googleapis.com/v0/b/lesswaste-28106.appspot.com/o/profile_images%2F869C3225-63B1-4C40-AAC9-66F9E3945520?alt=media&token=904bfc5c-61bb-4fc8-a71a-20dec8866b81",0,"ownerId","ingredientes","linkYT","#etiquetas","instrucciones"))
    }
}