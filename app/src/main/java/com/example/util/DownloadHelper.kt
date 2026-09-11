package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.data.local.EnquiryEntity
import com.example.model.CompanyContact
import com.example.model.DigitalService
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DownloadHelper {

    fun downloadAndShareFile(
        context: Context,
        fileName: String,
        mimeType: String,
        fileContent: String,
        chooserTitle: String = "Download & Save Document"
    ) {
        try {
            val downloadDir = File(context.cacheDir, "downloads")
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }

            val targetFile = File(downloadDir, fileName)
            FileOutputStream(targetFile).use { output ->
                output.write(fileContent.toByteArray(Charsets.UTF_8))
            }

            val authority = "${context.packageName}.fileprovider"
            val fileUri = FileProvider.getUriForFile(context, authority, targetFile)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, fileUri)
                putExtra(Intent.EXTRA_SUBJECT, fileName)
                putExtra(Intent.EXTRA_TEXT, "Downloaded document from DIGI NOVA: $fileName")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, chooserTitle).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
            Toast.makeText(context, "Downloaded '$fileName'. Choose where to save or share.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveAndShareFile(
        context: Context,
        fileName: String,
        mimeType: String,
        content: String,
        chooserTitle: String = "Download & Save Document"
    ) {
        downloadAndShareFile(
            context = context,
            fileName = fileName,
            mimeType = mimeType,
            fileContent = content,
            chooserTitle = chooserTitle
        )
    }

    fun downloadCompanyBrochure(
        context: Context,
        contact: CompanyContact,
        services: List<DigitalService>
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val generatedAt = dateFormat.format(Date())

        val builder = StringBuilder()
        builder.append("====================================================\n")
        builder.append("                DIGI NOVA SOLUTIONS                 \n")
        builder.append("            DIGITAL • SIMPLE • SMART               \n")
        builder.append("====================================================\n\n")
        builder.append("Official Company Brochure & Service Catalog\n")
        builder.append("Generated On: $generatedAt\n")
        builder.append("Owned & Led by: ${contact.ownerName}\n\n")
        builder.append("ABOUT DIGI NOVA:\n")
        builder.append("${contact.companyBio}\n\n")
        builder.append("----------------------------------------------------\n")
        builder.append("DIRECT CONTACT & SUPPORT:\n")
        builder.append("• WhatsApp: ${contact.supportWhatsAppNumber}\n")
        builder.append("• Phone: ${contact.supportPhoneNumber}\n")
        builder.append("• Email: ${contact.supportEmail}\n")
        builder.append("• Address: ${contact.address}\n")
        builder.append("• Operating Hours: ${contact.workingHours}\n")
        builder.append("----------------------------------------------------\n\n")
        builder.append("FEATURED DIGITAL SERVICES & SOLUTIONS:\n\n")

        services.filter { it.isEnabled }.forEachIndexed { index, service ->
            builder.append("${index + 1}. ${service.title.uppercase(Locale.getDefault())}\n")
            builder.append("   Category: ${service.category}\n")
            builder.append("   Price / Starting At: ${service.pricing}\n")
            builder.append("   Description: ${service.description}\n")
            if (service.features.isNotEmpty()) {
                builder.append("   Included Features:\n")
                service.features.forEach { feature ->
                    builder.append("     - $feature\n")
                }
            }
            builder.append("\n")
        }

        builder.append("====================================================\n")
        builder.append("© 2026 Anup Digi Nova. All Rights Reserved.\n")
        builder.append("DIGI NOVA — Bringing Digital Simplicity to Life.\n")
        builder.append("====================================================\n")

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_Company_Brochure_2026.txt",
            mimeType = "text/plain",
            fileContent = builder.toString(),
            chooserTitle = "Download DIGI NOVA Company Brochure"
        )
    }

    fun downloadServiceSummary(
        context: Context,
        service: DigitalService,
        contact: CompanyContact
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val generatedAt = dateFormat.format(Date())

        val builder = StringBuilder()
        builder.append("====================================================\n")
        builder.append("           DIGI NOVA SERVICE QUOTATION SHEET         \n")
        builder.append("====================================================\n\n")
        builder.append("Service: ${service.title}\n")
        builder.append("Category: ${service.category}\n")
        builder.append("Estimated Rate / Starting From: ${service.pricing}\n")
        builder.append("Date Issued: $generatedAt\n\n")
        builder.append("SERVICE OVERVIEW:\n")
        builder.append("${service.description}\n\n")
        if (service.features.isNotEmpty()) {
            builder.append("KEY DELIVERABLES & FEATURES:\n")
            service.features.forEach { feature ->
                builder.append("  [✓] $feature\n")
            }
            builder.append("\n")
        }
        builder.append("----------------------------------------------------\n")
        builder.append("HOW TO PROCEED:\n")
        builder.append("To book or customize this solution, contact ${contact.ownerName}:\n")
        builder.append("• WhatsApp: ${contact.supportWhatsAppNumber}\n")
        builder.append("• Call: ${contact.supportPhoneNumber}\n")
        builder.append("• Email: ${contact.supportEmail}\n")
        builder.append("----------------------------------------------------\n\n")
        builder.append("© 2026 Anup Digi Nova. All Rights Reserved.\n")

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_${service.title.replace(" ", "_")}_Quotation.txt",
            mimeType = "text/plain",
            fileContent = builder.toString(),
            chooserTitle = "Download Service Quotation"
        )
    }

    fun downloadApkGuide(
        context: Context,
        contact: CompanyContact
    ) {
        val content = """
            ====================================================
               DIGI NOVA ANDROID APK DOWNLOAD & INSTALL GUIDE
            ====================================================
            Platform: DIGI NOVA Digital Platform
            Owner/Developer: ${contact.ownerName}
            App ID: ${context.packageName}
            Version: ${contact.version}
            
            HOW TO GET THE APK:
            1. In the AI Studio Build interface, click on Settings (⚙️).
            2. Choose 'Export Project (ZIP)' or 'Generate APK'.
            3. Transfer or download the APK to your Android device.
            
            HOW TO INSTALL (SIDELOAD):
            1. Tap the downloaded APK file on your phone.
            2. When Android asks 'Install unknown apps', choose Allow.
            3. Tap Install.
            4. Launch DIGI NOVA!
            
            SUPPORT & INQUIRIES:
            WhatsApp: ${contact.supportWhatsAppNumber}
            Phone: ${contact.supportPhoneNumber}
            Email: ${contact.supportEmail}
            
            © 2026 Anup Digi Nova. All Rights Reserved.
            ====================================================
        """.trimIndent()

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_APK_Download_Guide.txt",
            mimeType = "text/plain",
            fileContent = content,
            chooserTitle = "Download APK Installation Guide"
        )
    }

    fun downloadEnquiryReceipt(
        context: Context,
        enquiryId: String,
        name: String,
        phone: String,
        email: String,
        serviceTitle: String,
        message: String,
        contact: CompanyContact
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateString = dateFormat.format(Date())

        val content = """
            ====================================================
                   DIGI NOVA CUSTOMER ENQUIRY RECEIPT
            ====================================================
            Reference ID: $enquiryId
            Submission Date: $dateString
            Status: PENDING REVIEW
            
            CLIENT INFORMATION:
            • Name: $name
            • Contact Phone: $phone
            • Email Address: $email
            
            REQUESTED SERVICE:
            • Solution: $serviceTitle
            
            PROJECT REQUIREMENTS / MESSAGE:
            $message
            
            ----------------------------------------------------
            NEXT STEPS:
            Our team led by ${contact.ownerName} will review your inquiry
            and reach out to you within 24 business hours.
            
            For urgent assistance:
            WhatsApp: ${contact.supportWhatsAppNumber} | Phone: ${contact.supportPhoneNumber}
            ----------------------------------------------------
            © 2026 Anup Digi Nova. All Rights Reserved.
        """.trimIndent()

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_Receipt_$enquiryId.txt",
            mimeType = "text/plain",
            fileContent = content,
            chooserTitle = "Download Enquiry Confirmation Receipt"
        )
    }

    fun downloadEnquiriesCsv(
        context: Context,
        enquiries: List<EnquiryEntity>
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val builder = StringBuilder()
        builder.append("ID,Date,Customer Name,Phone,Email,Service,Status,Message\n")

        enquiries.forEach { enq ->
            val dateStr = dateFormat.format(Date(enq.createdAt))
            val cleanMsg = enq.message.replace("\"", "\"\"").replace("\n", " ")
            builder.append("\"${enq.id}\",")
            builder.append("\"$dateStr\",")
            builder.append("\"${enq.customerName}\",")
            builder.append("\"${enq.customerPhone}\",")
            builder.append("\"${enq.customerEmail}\",")
            builder.append("\"${enq.serviceTitle}\",")
            builder.append("\"${enq.status}\",")
            builder.append("\"$cleanMsg\"\n")
        }

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_Customer_Enquiries_Export.csv",
            mimeType = "text/csv",
            fileContent = builder.toString(),
            chooserTitle = "Download Customer Enquiries (CSV)"
        )
    }

    fun downloadServicesJson(
        context: Context,
        services: List<DigitalService>
    ) {
        val builder = StringBuilder()
        builder.append("[\n")
        services.forEachIndexed { i, s ->
            val featuresList = s.features.joinToString(separator = "\", \"", prefix = "[\"", postfix = "\"]")
            builder.append("  {\n")
            builder.append("    \"id\": \"${s.id}\",\n")
            builder.append("    \"title\": \"${s.title}\",\n")
            builder.append("    \"category\": \"${s.category}\",\n")
            builder.append("    \"description\": \"${s.description.replace("\"", "\\\"")}\",\n")
            builder.append("    \"price\": \"${s.pricing}\",\n")
            builder.append("    \"features\": $featuresList,\n")
            builder.append("    \"isEnabled\": ${s.isEnabled}\n")
            builder.append("  }${if (i < services.size - 1) "," else ""}\n")
        }
        builder.append("]\n")

        downloadAndShareFile(
            context = context,
            fileName = "DIGI_NOVA_Services_Backup.json",
            mimeType = "application/json",
            fileContent = builder.toString(),
            chooserTitle = "Download Services Backup (JSON)"
        )
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
    }
}
