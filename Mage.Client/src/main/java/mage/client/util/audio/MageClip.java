/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util.audio;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author LevelX2
 */
public class MageClip {
    private static final Logger log = Logger.getLogger(MageClip.class);

    private final AudioGroup audioGroup;
    private final String filename;
    private final byte buf[];

    public MageClip(String filename, AudioGroup audioGroup) {
        this.filename = filename;
        this.audioGroup = audioGroup;
        this.buf = loadStream();
    }

    private byte[] loadStream() {
        File file = new File(filename);
        try {
            AudioInputStream soundIn = AudioSystem.getAudioInputStream(file);
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            copy(soundIn, bytesOut);
            return bytesOut.toByteArray();
        } catch (UnsupportedAudioFileException | IOException e) {
            log.warn("Failed to read " + filename, e);
            return null;
        }
    }

    private static void copy(InputStream source, OutputStream sink) throws IOException {
        byte[] buf = new byte[1024];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
        }
    }

    public AudioGroup getAudioGroup() {
        return audioGroup;
    }

    public byte[] getBuffer() {
        return buf;
    }

    public String getFilename() {
        return filename;
    }

}
