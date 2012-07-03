package strahinjakupusinac.android.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Patched input stream to be used in {@link BitmapUtils}. It solves "skia" bug: decoder->decode returned false.
 * 
 * @author Strahinja Kupusinac
 * 
 */
class PatchedInputStream extends FilterInputStream {

	public PatchedInputStream(InputStream in) {
		super(in);
	}

	public long skip(long n) throws IOException {
		long m = 0L;
		while (m < n) {
			long _m = in.skip(n - m);
			if (_m == 0L)
				break;
			m += _m;
		}
		return m;
	}

}
