package androidx.core.text.util;

import android.os.Build.VERSION;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.core.util.PatternsCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
    private static final Comparator<LinkSpec> COMPARATOR = new Comparator<LinkSpec>() {
        public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            if (linkSpec.start < linkSpec2.start) {
                return -1;
            }
            if (linkSpec.start > linkSpec2.start || linkSpec.end < linkSpec2.end) {
                return 1;
            }
            if (linkSpec.end > linkSpec2.end) {
                return -1;
            }
            return 0;
        }
    };
    private static final String[] EMPTY_STRING = new String[0];

    private static class LinkSpec {
        int end;
        URLSpan frameworkAddedSpan;
        int start;
        String url;

        LinkSpec() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkifyMask {
    }

    public static boolean addLinks(Spannable spannable, int i) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, i);
        }
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        if ((i & 4) != 0) {
            Linkify.addLinks(spannable, 4);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, PatternsCompat.AUTOLINK_WEB_URL, new String[]{"http://", "https://", "rtsp://"}, Linkify.sUrlMatchFilter, null);
        }
        if ((i & 2) != 0) {
            gatherLinks(arrayList, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((i & 8) != 0) {
            gatherMapLinks(arrayList, spannable);
        }
        pruneOverlaps(arrayList, spannable);
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next();
            if (linkSpec.frameworkAddedSpan == null) {
                applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable);
            }
        }
        return true;
    }

    public static boolean addLinks(TextView textView, int i) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(textView, i);
        }
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (!(text instanceof Spannable)) {
            SpannableString valueOf = SpannableString.valueOf(text);
            if (!addLinks((Spannable) valueOf, i)) {
                return false;
            }
            addLinkMovementMethod(textView);
            textView.setText(valueOf);
            return true;
        } else if (!addLinks((Spannable) text, i)) {
            return false;
        } else {
            addLinkMovementMethod(textView);
            return true;
        }
    }

    public static void addLinks(TextView textView, Pattern pattern, String str) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str);
        } else {
            addLinks(textView, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
        }
    }

    public static void addLinks(TextView textView, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str, matchFilter, transformFilter);
        } else {
            addLinks(textView, pattern, str, (String[]) null, matchFilter, transformFilter);
        }
    }

    public static void addLinks(TextView textView, Pattern pattern, String str, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            Linkify.addLinks(textView, pattern, str, strArr, matchFilter, transformFilter);
            return;
        }
        SpannableString valueOf = SpannableString.valueOf(textView.getText());
        if (addLinks((Spannable) valueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.setText(valueOf);
            addLinkMovementMethod(textView);
        }
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String str) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, pattern, str);
        }
        return addLinks(spannable, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, pattern, str, matchFilter, transformFilter);
        }
        return addLinks(spannable, pattern, str, (String[]) null, matchFilter, transformFilter);
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String str, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        String str2;
        if (shouldAddLinksFallbackToFramework()) {
            return Linkify.addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter);
        }
        String str3 = "";
        if (str == null) {
            str = str3;
        }
        if (strArr == null || strArr.length < 1) {
            strArr = EMPTY_STRING;
        }
        String[] strArr2 = new String[(strArr.length + 1)];
        strArr2[0] = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < strArr.length) {
            String str4 = strArr[i];
            i++;
            if (str4 == null) {
                str2 = str3;
            } else {
                str2 = str4.toLowerCase(Locale.ROOT);
            }
            strArr2[i] = str2;
        }
        Matcher matcher = pattern.matcher(spannable);
        boolean z = false;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, start, end) : true) {
                applyLink(makeUrl(matcher.group(0), strArr2, matcher, transformFilter), start, end, spannable);
                z = true;
            }
        }
        return z;
    }

    private static boolean shouldAddLinksFallbackToFramework() {
        return VERSION.SDK_INT >= 28;
    }

    private static void addLinkMovementMethod(TextView textView) {
        if (!(textView.getMovementMethod() instanceof LinkMovementMethod) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static String makeUrl(String str, String[] strArr, Matcher matcher, TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int i = 0;
        while (true) {
            z = true;
            if (i >= strArr.length) {
                z = false;
                break;
            }
            if (str.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                if (!str.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(strArr[i]);
                    sb.append(str.substring(strArr[i].length()));
                    str = sb.toString();
                }
            } else {
                i++;
            }
        }
        if (z || strArr.length <= 0) {
            return str;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strArr[0]);
        sb2.append(str);
        return sb2.toString();
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                LinkSpec linkSpec = new LinkSpec();
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.start = start;
                linkSpec.end = end;
                arrayList.add(linkSpec);
            }
        }
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable) {
        spannable.setSpan(new URLSpan(str), i, i2, 33);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0005 */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0005 A[LOOP:0: B:1:0x0005->B:14:0x0005, LOOP_START, PHI: r0 r6 
  PHI: (r0v1 int) = (r0v0 int), (r0v2 int) binds: [B:0:0x0000, B:14:0x0005] A[DONT_GENERATE, DONT_INLINE]
  PHI: (r6v2 java.lang.String) = (r6v1 java.lang.String), (r6v3 java.lang.String) binds: [B:0:0x0000, B:14:0x0005] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:1:0x0005] */
    private static void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        String obj = spannable.toString();
        int i = 0;
        while (true) {
            try {
                String findAddress = findAddress(obj);
                if (findAddress != null) {
                    int indexOf = obj.indexOf(findAddress);
                    if (indexOf >= 0) {
                        LinkSpec linkSpec = new LinkSpec();
                        int length = findAddress.length() + indexOf;
                        linkSpec.start = indexOf + i;
                        i += length;
                        linkSpec.end = i;
                        obj = obj.substring(length);
                        String encode = URLEncoder.encode(findAddress, "UTF-8");
                        StringBuilder sb = new StringBuilder();
                        sb.append("geo:0,0?q=");
                        sb.append(encode);
                        linkSpec.url = sb.toString();
                        arrayList.add(linkSpec);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } catch (UnsupportedOperationException unused) {
                return;
            }
        }
    }

    private static String findAddress(String str) {
        if (VERSION.SDK_INT >= 28) {
            return WebView.findAddress(str);
        }
        return FindAddress.findAddress(str);
    }

    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        int i = 0;
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i2 = 0; i2 < uRLSpanArr.length; i2++) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.frameworkAddedSpan = uRLSpanArr[i2];
            linkSpec.start = spannable.getSpanStart(uRLSpanArr[i2]);
            linkSpec.end = spannable.getSpanEnd(uRLSpanArr[i2]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int size = arrayList.size();
        while (i < size - 1) {
            LinkSpec linkSpec2 = (LinkSpec) arrayList.get(i);
            int i3 = i + 1;
            LinkSpec linkSpec3 = (LinkSpec) arrayList.get(i3);
            if (linkSpec2.start <= linkSpec3.start && linkSpec2.end > linkSpec3.start) {
                int i4 = (linkSpec3.end > linkSpec2.end && linkSpec2.end - linkSpec2.start <= linkSpec3.end - linkSpec3.start) ? linkSpec2.end - linkSpec2.start < linkSpec3.end - linkSpec3.start ? i : -1 : i3;
                if (i4 != -1) {
                    URLSpan uRLSpan = ((LinkSpec) arrayList.get(i4)).frameworkAddedSpan;
                    if (uRLSpan != null) {
                        spannable.removeSpan(uRLSpan);
                    }
                    arrayList.remove(i4);
                    size--;
                }
            }
            i = i3;
        }
    }

    private LinkifyCompat() {
    }
}
