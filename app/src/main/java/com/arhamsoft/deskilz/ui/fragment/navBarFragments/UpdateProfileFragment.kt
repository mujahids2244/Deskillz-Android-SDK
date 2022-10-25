package com.arhamsoft.deskilz.ui.fragment.navBarFragments

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.arhamsoft.deskilz.R
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.databinding.FragmentUpdateProfileBinding
import com.arhamsoft.deskilz.domain.listeners.NetworkListener
import com.arhamsoft.deskilz.domain.repository.NetworkRepo
import com.arhamsoft.deskilz.networking.networkModels.UpdateProfileModel
import com.arhamsoft.deskilz.networking.retrofit.URLConstant
import com.arhamsoft.deskilz.utils.LoadingDialog
import com.arhamsoft.deskilz.utils.StaticFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class UpdateProfileFragment : Fragment() {

    lateinit var binding:FragmentUpdateProfileBinding
    lateinit var loading:LoadingDialog
    private var u_id:String? = " "
    var bitmap: Bitmap?= null
    var encodedImage:String? = null
     var filePart: MultipartBody.Part? = null

    lateinit var sharedPreference: CustomSharedPreference




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(LayoutInflater.from(context))
        // Inflate the layout for this fragment
        loading = LoadingDialog(requireContext() as Activity)
        sharedPreference = CustomSharedPreference(requireContext())


        binding.etname.setText(sharedPreference.returnValue("USERNAME"))
        binding.shoutout.setText(sharedPreference.returnValue("SHOUTOUT"))

        if(sharedPreference.returnValue("USERIMG") != null) {

            val img=  sharedPreference.returnValue("USERIMG")
//            bitmap = byteArray?.let { Imgconvertors.toBitmap(it) }!!
            binding.uploadpic.load(img){
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val setImg =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            val data: Intent? = result.data

            if (data != null) {
                bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
                binding.uploadpic.setImageBitmap(bitmap)

                val selectedImageURI = data.data
                val imageFile = File(getRealPathFromURI(selectedImageURI!!)!!)
                if (imageFile != null) {
                    try {
                        if (imageFile.exists()) {
//                multipart/form-data
                            val fileBody: RequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                            filePart = createFormData("avatar", imageFile.name, fileBody)
                        }
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
                else{
                    val attachmentEmpty: RequestBody= " ".toRequestBody("text/plain".toMediaTypeOrNull());

                    filePart = createFormData("attachment", "", attachmentEmpty);
                }

//                val imageUri: Uri? = data.data
//                val imageStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)
//                val selectedImage = BitmapFactory.decodeStream(imageStream)
//                encodedImage = encodeImage(selectedImage)
//
////                if (encodedImage!!.contains("/9j") == true){
//////                    updatedEncodedImage = encodedImage!!.replace("/9j","data:image/jpeg;base64,/9j")
////
//                }




            }

        }


        binding.uploadpic.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            setImg.launch(gallery)

        }




        binding.backtoProfile.setOnClickListener {
            findNavController().popBackStack()
        }
        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserDatabase.getDatabase(requireContext()).userDao().getUser()
//            if (user != null) {
//                u_id = user.userId
//            }
            binding.updateupbtn.setOnClickListener {

                if (binding.etname.text!!.isEmpty() && binding.shoutout.text!!.isEmpty()) {
                    binding.etname.error = "Name Field is Empty"
                    binding.shoutout.error = "Shoutout Field is Empty"
                } else if (binding.etname.text!!.isEmpty()) {
                    binding.etname.requestFocus()
                    binding.etname.error = "Email Field is Empty"

                } else if (binding.shoutout.text!!.isEmpty()) {
                    binding.shoutout.requestFocus()
                    binding.shoutout.error = "Shoutout Field is Empty"

                } else {
//                if (filePart == null){
//                    filePar
//                }
                    loading.startLoading()
                    updateProfile()
                }
            }
        }
    }


//    private fun galleryLauncher(){
//
//    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = requireContext().contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
//    //bitmap to base 64
//    private fun encodeImage(bm: Bitmap): String? {
//        val baos = ByteArrayOutputStream()
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val b = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }
//
//    private fun StringToBitMap(encodedString: String?): Bitmap? {
//        return try {
//            val encodeByte =
//                Base64.decode(encodedString, Base64.DEFAULT)
//            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
//        } catch (e: Exception) {
//            e.message
//            null
//        }
//    }


    private fun updateProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepo.updateProfile(
                URLConstant.u_id!!,
                binding.etname.text.toString(),
                binding.shoutout.text.toString(),
                filePart!!,
                object : NetworkListener<UpdateProfileModel> {
                    override fun successFul(t: UpdateProfileModel) {
                        loading.isDismiss()
                        if(t.status == 1) {

                            activity?.runOnUiThread {
                                sharedPreference.saveValue("USERIMG",t.data.userImage)
                                sharedPreference.saveValue("USERNAME",t.data.userName)
                                sharedPreference.saveValue("SHOUTOUT",t.data.userShoutOut)

                                StaticFields.toastClass(t.message)
                                findNavController().popBackStack()

                            }


                        }


                    }




                    override fun failure() {
                        loading.isDismiss()

                        activity?.runOnUiThread {
                            StaticFields.toastClass("Api syncing fail user detail info ")
                        }
                    }
                }
            )
        }

    }

}