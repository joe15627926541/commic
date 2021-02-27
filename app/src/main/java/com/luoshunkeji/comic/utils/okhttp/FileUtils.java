package com.luoshunkeji.comic.utils.okhttp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件操作相关工具类
 *
 * @author rj-liang
 * @date 2016/3/8
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    private static Random sRandom = new Random(SystemClock.uptimeMillis());

    // file extension array
    private static String[] mFileTypes = new String[]{ "apk", "avi", "bat", "bin", "bmp", "chm", "css", "dat", "dll", "doc", "docx", "dos", "dvd", "db", "flv", "gif", "html", "java", "jpeg", "jpg",
            "log", "m4a", "mid", "mkv", "mov", "movie", "mp3", "mp4", "mpe", "mpeg", "mpg", "pdf", "php", "png", "ppt", "pptx", "psd", "rar", "rmv", "rmvb", "swf", "tif", "ttf", "txt", "wav", "wma",
            "wmv", "xls", "xlsx", "xml", "xsl", "zip", "vcf" };

    private static String[] mWpsFileTypes = new String[] { "ppt", "pptx", "pot", "potx", "pps", "ppsx", "dps", "dpt", "pptm", "potm", "ppsm"};

    private static boolean isMatchID = false;
    private static boolean isMatchIconID = false;
    private static Drawable mFileIcon = null;
    private static Drawable mFolderIcon = null;

    public static boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /***
     * 删除文件
     */
    public static void deleteFile(File file) {
        if (file != null
                && file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /***
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        deleteFile(new File(filePath));
    }

    /***
     * 获取文件扩展名
     *
     * @param filename 文件名
     */
    public static String getFileExtName(String filename) {
        String extName = "";
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                extName = filename.substring(dot);
            }
        }
        return extName;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param filename 文件名
     */
    public static String getFileNameWithoutExt(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 自增序号式文件更名
     *
     * @param filename 文件名
     * @return
     */
    public static String renameIncrementFilename(String filename) {
        String pregax = "\\(\\d*\\)";
        Pattern pattern = Pattern.compile(pregax);
        Matcher matcher = pattern.matcher(filename);
        boolean find = matcher.find();
        if (find) {
            String group = matcher.group();
            String num = group.replace("(", "");
            num = num.replace(")", "");
            int parseInt = Integer.parseInt(num);
            parseInt++;
            String finalString = matcher.replaceAll("(" + String.valueOf(parseInt) + ")");
            return finalString;
        } else {
            String spliteWithoutExt = getFileNameWithoutExt(filename);
            String extName = getFileExtName(filename);
            String newName = spliteWithoutExt + "(1)" + extName;
            return newName;
        }
    }



    /**
     * Get MIME type of the file
     *
     * @param file The name of the file.
     * @return mimetype the MIME Type of the file.
     * @throws
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String name = file.getName();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
        if (end.equals(""))
            return type;
        for (int i = 0; i < MIMEType_Table.length; i++) {
            if (end.equals(MIMEType_Table[i][0]))
                type = MIMEType_Table[i][1];
        }
        return type;
    }

    /**
     * Get MIME type of the file
     *
     * @param fileName The name of the file.
     * @return mimetype the MIME Type of the file.
     * @throws
     */
    public static String getMIMETypeByFileName(String fileName) {
        String type = "*/*";

        if (TextUtils.isEmpty(fileName)) {
            return type;
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        if (end.equals(""))
            return type;
        for (int i = 0; i < MIMEType_Table.length; i++) {
            if (end.equals(MIMEType_Table[i][0]))
                type = MIMEType_Table[i][1];
        }
        return type;
    }

    /**
     * MIME type Array
     */
    public static final String[][] MIMEType_Table = {
            // {extension, MIMEType}
            { "3gp", "video/3gpp" },
            { "aab", "application/x-authoware-bin" },
            { "aam", "application/x-authoware-map" },
            { "aas", "application/x-authoware-seg" },
            { "ai", "application/postscript" },
            { "aif", "audio/x-aiff" },
            { "aifc", "audio/x-aiff" },
            { "aiff", "audio/x-aiff" },
            { "als", "audio/X-Alpha5" },
            { "amc", "application/x-mpeg" },
            { "ani", "application/octet-stream" },
            { "apk", "application/vnd.android.package-archive" },
            { "asc", "text/plain" },
            { "asd", "application/astound" },
            { "asf", "video/x-ms-asf" },
            { "asn", "application/astound" },
            { "asp", "application/x-asap" },
            { "asx", "video/x-ms-asf" },
            { "au", "audio/basic" },
            { "avb", "application/octet-stream" },
            { "avi", "video/x-msvideo" },
            { "awb", "audio/amr-wb" },
            { "bcpio", "application/x-bcpio" },
            { "bin", "application/octet-stream" },
            { "bld", "application/bld" },
            { "bld2", "application/bld2" },
            { "bmp", "image/bmp" },
            { "bpk", "application/octet-stream" },
            { "bz2", "application/x-bzip2" },
            { "c", "text/x-csrc" },
            { "cpp", "text/x-c++src" },
            { "cal", "image/x-cals" },
            { "ccn", "application/x-cnc" },
            { "cco", "application/x-cocoa" },
            { "cdf", "application/x-netcdf" },
            { "cgi", "magnus-internal/cgi" },
            { "chat", "application/x-chat" },
            { "class", "application/octet-stream" },
            { "clp", "application/x-msclip" },
            { "cmx", "application/x-cmx" },
            { "co", "application/x-cult3d-object" },
            { "cod", "image/cis-cod" },
            { "cpio", "application/x-cpio" },
            { "cpt", "application/mac-compactpro" },
            { "crd", "application/x-mscardfile" },
            { "csh", "application/x-csh" },
            { "csm", "chemical/x-csml" },
            { "csml", "chemical/x-csml" },
            { "css", "text/css" },
            { "cur", "application/octet-stream" },
            { "dcm", "x-lml/x-evm" },
            { "dcr", "application/x-director" },
            { "dcx", "image/x-dcx" },
            { "dhtml", "text/html" },
            { "dir", "application/x-director" },
            { "dll", "application/octet-stream" },
            { "dmg", "application/octet-stream" },
            { "dms", "application/octet-stream" },
            { "doc", "application/msword" },
            { "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { "dot", "application/x-dot" },
            { "dvi", "application/x-dvi" },
            { "dwf", "drawing/x-dwf" },
            { "dwg", "application/x-autocad" },
            { "dxf", "application/x-autocad" },
            { "dxr", "application/x-director" },
            { "ebk", "application/x-expandedbook" },
            { "emb", "chemical/x-embl-dl-nucleotide" },
            { "embl", "chemical/x-embl-dl-nucleotide" },
            { "eps", "application/postscript" },
            { "eri", "image/x-eri" },
            { "es", "audio/echospeech" },
            { "esl", "audio/echospeech" },
            { "etc", "application/x-earthtime" },
            { "etx", "text/x-setext" },
            { "evm", "x-lml/x-evm" },
            { "evy", "application/x-envoy" },
            { "exe", "application/octet-stream" },
            { "fh4", "image/x-freehand" },
            { "fh5", "image/x-freehand" },
            { "fhc", "image/x-freehand" },
            { "fif", "image/fif" },
            { "fm", "application/x-maker" },
            { "fpx", "image/x-fpx" },
            { "fvi", "video/isivideo" },
            { "gau", "chemical/x-gaussian-input" },
            { "gca", "application/x-gca-compressed" },
            { "gdb", "x-lml/x-gdb" },
            { "gif", "image/gif" },
            { "gps", "application/x-gps" },
            { "gtar", "application/x-gtar" },
            { "gz", "application/x-gzip" },
            { "h", "text/x-chdr" },
            { "hdf", "application/x-hdf" },
            { "hdm", "text/x-hdml" },
            { "hdml", "text/x-hdml" },
            { "hlp", "application/winhlp" },
            { "hqx", "application/mac-binhex40" },
            { "htm", "text/html" },
            { "html", "text/html" },
            { "hts", "text/html" },
            { "ice", "x-conference/x-cooltalk" },
            { "ico", "application/octet-stream" },
            { "ief", "image/ief" },
            { "ifm", "image/gif" },
            { "ifs", "image/ifs" },
            { "imy", "audio/melody" },
            { "ins", "application/x-NET-Install" },
            { "ips", "application/x-ipscript" },
            { "ipx", "application/x-ipix" },
            { "it", "audio/x-mod" },
            { "itz", "audio/x-mod" },
            { "ivr", "i-world/i-vrml" },
            { "j2k", "image/j2k" },
            { "jad", "text/vnd.sun.j2me.app-descriptor" },
            { "jam", "application/x-jam" },
            { "java", "application/x-tex" },
            { "jar", "application/java-archive" },
            { "jnlp", "application/x-java-jnlp-file" },
            { "jpe", "image/jpeg" },
            { "jpeg", "image/jpeg" },
            { "jpg", "image/jpeg" },
            { "jpz", "image/jpeg" },
            { "js", "application/x-javascript" },
            { "jwc", "application/jwc" },
            { "kjx", "application/x-kjx" },
            { "lak", "x-lml/x-lak" },
            { "latex", "application/x-latex" },
            { "lcc", "application/fastman" },
            { "lcl", "application/x-digitalloca" },
            { "lcr", "application/x-digitalloca" },
            { "lgh", "application/lgh" },
            { "lha", "application/octet-stream" },
            { "lml", "x-lml/x-lml" },
            { "lmlpack", "x-lml/x-lmlpack" },
            { "lsf", "video/x-ms-asf" },
            { "lsx", "video/x-ms-asf" },
            { "lzh", "application/x-lzh" },
            { "m13", "application/x-msmediaview" },
            { "m14", "application/x-msmediaview" },
            { "m15", "audio/x-mod" },
            { "m3u", "audio/x-mpegurl" },
            { "m3url", "audio/x-mpegurl" },
            { "ma1", "audio/ma1" },
            { "ma2", "audio/ma2" },
            { "ma3", "audio/ma3" },
            { "ma5", "audio/ma5" },
            { "man", "application/x-troff-man" },
            { "map", "magnus-internal/imagemap" },
            { "mbd", "application/mbedlet" },
            { "mct", "application/x-mascot" },
            { "mdb", "application/x-msaccess" },
            { "mdz", "audio/x-mod" },
            { "me", "application/x-troff-me" },
            { "mel", "text/x-vmel" },
            { "mi", "application/x-mif" },
            { "mid", "audio/midi" },
            { "midi", "audio/midi" },
            { "mif", "application/x-mif" },
            { "mil", "image/x-cals" },
            { "mio", "audio/x-mio" },
            { "mmf", "application/x-skt-lbs" },
            { "mng", "video/x-mng" },
            { "mny", "application/x-msmoney" },
            { "moc", "application/x-mocha" },
            { "mocha", "application/x-mocha" },
            { "mod", "audio/x-mod" },
            { "mof", "application/x-yumekara" },
            { "mol", "chemical/x-mdl-molfile" },
            { "mop", "chemical/x-mopac-input" },
            { "mov", "video/quicktime" },
            { "movie", "video/x-sgi-movie" },
            { "mp2", "audio/x-mpeg" },
            { "mp3", "audio/x-mpeg" },
            { "mp4", "video/mp4" },
            { "mpc", "application/vnd.mpohun.certificate" },
            { "mpe", "video/mpeg" },
            { "mpeg", "video/mpeg" },
            { "mpg", "video/mpeg" },
            { "mpg4", "video/mp4" },
            { "mpga", "audio/mpeg" },
            { "mpn", "application/vnd.mophun.application" },
            { "mpp", "application/vnd.ms-project" },
            { "mps", "application/x-mapserver" },
            { "mrl", "text/x-mrml" },
            { "mrm", "application/x-mrm" },
            { "ms", "application/x-troff-ms" },
            { "mts", "application/metastream" },
            { "mtx", "application/metastream" },
            { "mtz", "application/metastream" },
            { "mzv", "application/metastream" },
            { "nar", "application/zip" },
            { "nbmp", "image/nbmp" },
            { "nc", "application/x-netcdf" },
            { "ndb", "x-lml/x-ndb" },
            { "ndwn", "application/ndwn" },
            { "nif", "application/x-nif" },
            { "nmz", "application/x-scream" },
            { "nokia-op-logo", "image/vnd.nok-oplogo-color" },
            { "npx", "application/x-netfpx" },
            { "nsnd", "audio/nsnd" },
            { "nva", "application/x-neva1" },
            { "oda", "application/oda" },
            { "oom", "application/x-AtlasMate-Plugin" },
            { "pac", "audio/x-pac" },
            { "pae", "audio/x-epac" },
            { "pan", "application/x-pan" },
            { "pbm", "image/x-portable-bitmap" },
            { "pcx", "image/x-pcx" },
            { "pda", "image/x-pda" },
            { "pdb", "chemical/x-pdb" },
            { "pdf", "application/pdf" },
            { "pfr", "application/font-tdpfr" },
            { "pgm", "image/x-portable-graymap" },
            { "pict", "image/x-pict" },
            { "pm", "application/x-perl" },
            { "pmd", "application/x-pmd" },
            { "png", "image/png" },
            { "pnm", "image/x-portable-anymap" },
            { "pnz", "image/png" },
            { "pot", "application/vnd.ms-powerpoint" },
            { "ppm", "image/x-portable-pixmap" },
            { "pps", "application/vnd.ms-powerpoint" },
            { "ppt", "application/vnd.ms-powerpoint" },
            { "pqf", "application/x-cprplayer" },
            { "pqi", "application/cprplayer" },
            { "prc", "application/x-prc" },
            { "proxy", "application/x-ns-proxy-autoconfig" },
            { "ps", "application/postscript" },
            { "ptlk", "application/listenup" },
            { "pub", "application/x-mspublisher" },
            { "pvx", "video/x-pv-pvx" },
            { "qcp", "audio/vnd.qcelp" },
            { "qt", "video/quicktime" },
            { "pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
            { "qti", "image/x-quicktime" },
            { "qtif", "image/x-quicktime" },
            { "r3t", "text/vnd.rn-realtext3d" },
            { "ra", "audio/x-pn-realaudio" },
            { "ram", "audio/x-pn-realaudio" },
            { "rar", "application/x-rar-compressed" },
            { "ras", "image/x-cmu-raster" },
            { "rdf", "application/rdf+xml" },
            { "rf", "image/vnd.rn-realflash" },
            { "rgb", "image/x-rgb" },
            { "rlf", "application/x-richlink" },
            { "rm", "audio/x-pn-realaudio" },
            { "rmf", "audio/x-rmf" },
            { "rmm", "audio/x-pn-realaudio" },
            { "rmvb", "audio/x-pn-realaudio" },
            { "rnx", "application/vnd.rn-realplayer" },
            { "roff", "application/x-troff" },
            { "rp", "image/vnd.rn-realpix" },
            { "rpm", "audio/x-pn-realaudio-plugin" },
            { "rt", "text/vnd.rn-realtext" },
            { "rte", "x-lml/x-gps" },
            { "rtf", "application/rtf" },
            { "rtg", "application/metastream" },
            { "rtx", "text/richtext" },
            { "rv", "video/vnd.rn-realvideo" },
            { "rwc", "application/x-rogerwilco" },
            { "s3m", "audio/x-mod" },
            { "s3z", "audio/x-mod" },
            { "sca", "application/x-supercard" },
            { "scd", "application/x-msschedule" },
            { "sdf", "application/e-score" },
            { "sea", "application/x-stuffit" },
            { "sgm", "text/x-sgml" },
            { "sgml", "text/x-sgml" },
            { "sh", "application/x-sh" },
            { "shar", "application/x-shar" },
            { "shtml", "magnus-internal/parsed-html" },
            { "shw", "application/presentations" },
            { "si6", "image/si6" },
            { "si7", "image/vnd.stiwap.sis" },
            { "si9", "image/vnd.lgtwap.sis" },
            { "sis", "application/vnd.symbian.install" },
            { "sit", "application/x-stuffit" },
            { "skd", "application/x-Koan" },
            { "skm", "application/x-Koan" },
            { "skp", "application/x-Koan" },
            { "skt", "application/x-Koan" },
            { "slc", "application/x-salsa" },
            { "smd", "audio/x-smd" },
            { "smi", "application/smil" },
            { "smil", "application/smil" },
            { "smp", "application/studiom" },
            { "smz", "audio/x-smd" },
            { "snd", "audio/basic" },
            { "spc", "text/x-speech" },
            { "spl", "application/futuresplash" },
            { "spr", "application/x-sprite" },
            { "sprite", "application/x-sprite" },
            { "spt", "application/x-spt" },
            { "src", "application/x-wais-source" },
            { "stk", "application/hyperstudio" },
            { "stm", "audio/x-mod" },
            { "sv4cpio", "application/x-sv4cpio" },
            { "sv4crc", "application/x-sv4crc" },
            { "svf", "image/vnd" },
            { "svg", "image/svg-xml" },
            { "svh", "image/svh" },
            { "svr", "x-world/x-svr" },
            { "swf", "application/x-shockwave-flash" },
            { "swfl", "application/x-shockwave-flash" },
            { "t", "application/x-troff" },
            { "tad", "application/octet-stream" },
            { "talk", "text/x-speech" },
            { "tar", "application/x-tar" },
            { "taz", "application/x-tar" },
            { "tbp", "application/x-timbuktu" },
            { "tbt", "application/x-timbuktu" },
            { "tcl", "application/x-tcl" },
            { "tex", "application/x-tex" },
            { "texi", "application/x-texinfo" },
            { "texinfo", "application/x-texinfo" },
            { "tgz", "application/x-tar" },
            { "thm", "application/vnd.eri.thm" },
            { "tif", "image/tiff" },
            { "tiff", "image/tiff" },
            { "tki", "application/x-tkined" },
            { "tkined", "application/x-tkined" },
            { "toc", "application/toc" },
            { "toy", "image/toy" },
            { "tr", "application/x-troff" },
            { "trk", "x-lml/x-gps" },
            { "trm", "application/x-msterminal" },
            { "tsi", "audio/tsplayer" },
            { "tsp", "application/dsptype" },
            { "tsv", "text/tab-separated-values" },
            { "tsv", "text/tab-separated-values" },
            { "ttf", "application/octet-stream" },
            { "ttz", "application/t-time" },
            { "txt", "text/plain" },
            { "ult", "audio/x-mod" },
            { "ustar", "application/x-ustar" },
            { "uu", "application/x-uuencode" },
            { "uue", "application/x-uuencode" },
            { "vcd", "application/x-cdlink" },
            { "vcf", "text/x-vcard" },
            { "vdo", "video/vdo" },
            { "vib", "audio/vib" },
            { "viv", "video/vivo" },
            { "vivo", "video/vivo" },
            { "vmd", "application/vocaltec-media-desc" },
            { "vmf", "application/vocaltec-media-file" },
            { "vmi", "application/x-dreamcast-vms-info" },
            { "vms", "application/x-dreamcast-vms" },
            { "vox", "audio/voxware" },
            { "vqe", "audio/x-twinvq-plugin" },
            { "vqf", "audio/x-twinvq" },
            { "vql", "audio/x-twinvq" },
            { "vre", "x-world/x-vream" },
            { "vrml", "x-world/x-vrml" },
            { "vrt", "x-world/x-vrt" },
            { "vrw", "x-world/x-vream" },
            { "vts", "workbook/formulaone" },
            { "wav", "audio/x-wav" },
            { "wax", "audio/x-ms-wax" },
            { "wbmp", "image/vnd.wap.wbmp" },
            { "web", "application/vnd.xara" },
            { "wi", "image/wavelet" },
            { "wis", "application/x-InstallShield" },
            { "wm", "video/x-ms-wm" },
            { "wma", "audio/x-ms-wma" },
            { "wmd", "application/x-ms-wmd" },
            { "wmf", "application/x-msmetafile" },
            { "wml", "text/vnd.wap.wml" },
            { "wmlc", "application/vnd.wap.wmlc" },
            { "wmls", "text/vnd.wap.wmlscript" },
            { "wmlsc", "application/vnd.wap.wmlscriptc" },
            { "wmlscript", "text/vnd.wap.wmlscript" },
            { "wmv", "audio/x-ms-wmv" },
            { "wmx", "video/x-ms-wmx" },
            { "wmz", "application/x-ms-wmz" },
            { "wps", "application/msword" },
            { "wpng", "image/x-up-wpng" },
            { "wpt", "x-lml/x-gps" },
            { "wri", "application/x-mswrite" },
            { "wrl", "x-world/x-vrml" },
            { "wrz", "x-world/x-vrml" },
            { "ws", "text/vnd.wap.wmlscript" },
            { "wsc", "application/vnd.wap.wmlscriptc" },
            { "wv", "video/wavelet" },
            { "wvx", "video/x-ms-wvx" },
            { "wxl", "application/x-wxl" },
            { "x-gzip", "application/x-gzip" },
            { "xar", "application/vnd.xara" },
            { "xbm", "image/x-xbitmap" },
            { "xdm", "application/x-xdma" },
            { "xdma", "application/x-xdma" },
            { "xdw", "application/vnd.fujixerox.docuworks" },
            { "xht", "application/xhtml+xml" },
            { "xhtm", "application/xhtml+xml" },
            { "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { "xhtml", "application/xhtml+xml" },
            { "xla", "application/vnd.ms-excel" },
            { "xlc", "application/vnd.ms-excel" },
            { "xll", "application/x-excel" },
            { "xlm", "application/vnd.ms-excel" },
            { "xls", "application/vnd.ms-excel" },
            { "xlt", "application/vnd.ms-excel" },
            { "xlw", "application/vnd.ms-excel" },
            { "xm", "audio/x-mod" },
            { "xml", "text/xml" },
            { "xmz", "audio/x-mod" },
            { "xpi", "application/x-xpinstall" },
            { "xpm", "image/x-xpixmap" },
            { "xsit", "text/xml" },
            { "xsl", "text/xml" },
            { "xul", "text/xul" },
            { "xwd", "image/x-xwindowdump" },
            { "xyz", "chemical/x-pdb" },
            { "yz1", "application/x-yz1" },
            { "z", "application/x-compress" },
            { "zac", "application/x-zaurus-zac" },
            { "zip", "application/zip" },
    };

    /**
     * file size
     *
     * @param f
     * @return
     */
    public static String fileSizeMsg(File f) {
        String show = "";

        // is file
        if (f.isFile()) {
            long length = f.length();
            show = convertSize(length);
        }

        // is directory
        if (f.isDirectory()) {
            show = "";
        }
        return show;
    }

    /**
     * unit convert
     *
     * @param length
     * @return
     */
    public static String convertSize(long length) {
        int subIndex = 0;
        String fileSize = "";

		/*
         * convert to GB 1G=1024x1024x1024=1073741824
		 */
        if (length >= 1073741824) {
            subIndex = (String.valueOf((float) length / 1073741824)).indexOf(".");
            fileSize = ((float) length / 1073741824 + "000").substring(0, subIndex + 3) + "G";
        }

		/*
         * convert to MB 1MB = 1024x1024
		 */
        else if (length >= 1048576) {
            subIndex = (String.valueOf((float) length / 1048576)).indexOf(".");
            fileSize = ((float) length / 1048576 + "000").substring(0, subIndex + 3) + "M";
        }

		/* convert to KB */
        else if (length >= 1024) {
            subIndex = (String.valueOf((float) length / 1024)).indexOf(".");
            fileSize = ((float) length / 1024 + "000").substring(0, subIndex + 3) + "K";
        }

		/* convert to Byte */
        else if (length < 1024) {
            fileSize = String.valueOf(length) + "B";
        }
        return fileSize;
    }


    public static boolean isWPSFileType(File file) {
        boolean isWPSFileType = false;

        if (file.isDirectory()) {
            return true;
        }

        String filePath = file.getPath();
        for (int i = 0; i < mWpsFileTypes.length; i++) {
            if(filePath.toLowerCase().endsWith(mWpsFileTypes[i])) {
                isWPSFileType = true;
                break;
            }
        }

        return isWPSFileType;
    }

    public static int matchFileIconID(Context context, String fileName, int defaultIconID) {
        int drawableID = -1;
        int pointIndex = fileName.lastIndexOf(".");
        String type = fileName.substring(pointIndex + 1).toLowerCase(Locale.ENGLISH);
        for (int i = 0; i < mFileTypes.length; i++) {

            if (type.equals(mFileTypes[i])) {
                // get the image resource Id
                drawableID = context.getResources().getIdentifier(type, "drawable", context.getPackageName());
                isMatchIconID = true;
            }
        }

        if (!isMatchIconID) {
            drawableID = defaultIconID;
        }

        isMatchIconID = false;
        return drawableID;

    }

    /**
     * @param path delete dirs recursively
     */
    public static void deleteDir(File path) {
        if (path == null || !path.exists()) {
            return;
        }

        if (path.isDirectory()) {
            if (path.listFiles() == null) {
                // delete empty dir
                path.delete();
            } else {
                for (File file : path.listFiles()) {
                    if (file.isFile()) {
                        // delete file
                        file.delete();
                    } else if (file.isDirectory()) {
                        // delete dir which is not empty
                        deleteDir(file);

                        // delete dir whose children files are deleted
                        file.delete();
                    }
                }
            }
        }
    }

    /*
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        String extensionName = "";
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                extensionName = filename.substring(dot);
            }
        }
        return extensionName;
    }

    /*
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionNameNoDot(String filename) {
        String extensionName = "";
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                extensionName = filename.substring(dot + 1);
            }
        }
        return extensionName;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static void renameFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        if (oldFile.exists()) {
            oldFile.renameTo(newFile); // 执行重命名
        }
    }

    /**
     * 拷贝文件的函数,拷贝的过程其实就是把src文件里内容写入dst文件里的过程
     *
     * @param src 需要复制的文件
     * @param dst 目标文件(被src覆盖的文件)
     * @throws IOException
     */
    public static boolean copyFile(File src, File dst) throws IOException {
        boolean result = true;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dst).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
            throw e;
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result = false;
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result = false;
                }
            }
        }

        return result;
    }

    private static long getTotalSizeOfFilesInDir(final ExecutorService service,
                                                 final File file) throws InterruptedException, ExecutionException,
            TimeoutException {
        if (file.isFile())
            return file.length();
        long total = 0;
        final File[] children = file.listFiles();
        if (children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
            for (final File child : children) {
                partialTotalFutures.add(service.submit(new Callable<Long>() {
                    public Long call() throws InterruptedException,
                            ExecutionException, TimeoutException {
                        return getTotalSizeOfFilesInDir(service, child);
                    }
                }));
            }
            for (final Future<Long> partialTotalFuture : partialTotalFutures)
                total += partialTotalFuture.get(100, TimeUnit.SECONDS);
        }
        return total;
    }

    public static long getTotalSizeOfFile(final File file)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private final AtomicInteger id = new AtomicInteger(0);
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Executor-getTotalSizeOfFile"+ id.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
        try {
            return getTotalSizeOfFilesInDir(service, file);
        } finally {
            service.shutdown();
        }
    }

    public static boolean isFileExist(String path) {
        try {
            if (path == null || path.isEmpty()) {
                return false;
            }

            File file = new File(path);
            return file.exists() && file.isFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getFileSize(String path) {
        File file = new File(path);
        try {
            if (file.exists() && file.isFile()) {
                return file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

}
