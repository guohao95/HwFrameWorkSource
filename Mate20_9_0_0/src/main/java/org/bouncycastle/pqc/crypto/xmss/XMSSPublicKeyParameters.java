package org.bouncycastle.pqc.crypto.xmss;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public final class XMSSPublicKeyParameters extends AsymmetricKeyParameter implements XMSSStoreableObjectInterface {
    private final XMSSParameters params;
    private final byte[] publicSeed;
    private final byte[] root;

    public static class Builder {
        private final XMSSParameters params;
        private byte[] publicKey = null;
        private byte[] publicSeed = null;
        private byte[] root = null;

        public Builder(XMSSParameters xMSSParameters) {
            this.params = xMSSParameters;
        }

        public XMSSPublicKeyParameters build() {
            return new XMSSPublicKeyParameters(this);
        }

        public Builder withPublicKey(byte[] bArr) {
            this.publicKey = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withPublicSeed(byte[] bArr) {
            this.publicSeed = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withRoot(byte[] bArr) {
            this.root = XMSSUtil.cloneArray(bArr);
            return this;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:27:0x0060 in {6, 8, 10, 15, 17, 18, 23, 25, 26, 29} preds:[]
        	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:242)
        	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:42)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1257)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
        */
    private XMSSPublicKeyParameters(org.bouncycastle.pqc.crypto.xmss.XMSSPublicKeyParameters.Builder r5) {
        /*
        r4 = this;
        r0 = 0;
        r4.<init>(r0);
        r1 = r5.params;
        r4.params = r1;
        r1 = r4.params;
        if (r1 == 0) goto L_0x0061;
        r1 = r4.params;
        r1 = r1.getDigestSize();
        r2 = r5.publicKey;
        if (r2 == 0) goto L_0x0035;
        r5 = r1 + r1;
        r3 = r2.length;
        if (r3 != r5) goto L_0x002d;
        r5 = org.bouncycastle.pqc.crypto.xmss.XMSSUtil.extractBytesAtOffset(r2, r0, r1);
        r4.root = r5;
        r0 = r0 + r1;
        r5 = org.bouncycastle.pqc.crypto.xmss.XMSSUtil.extractBytesAtOffset(r2, r0, r1);
        r4.publicSeed = r5;
        return;
        r5 = new java.lang.IllegalArgumentException;
        r0 = "public key has wrong size";
        r5.<init>(r0);
        throw r5;
        r0 = r5.root;
        if (r0 == 0) goto L_0x0047;
        r2 = r0.length;
        if (r2 != r1) goto L_0x003f;
        goto L_0x0049;
        r5 = new java.lang.IllegalArgumentException;
        r0 = "length of root must be equal to length of digest";
        r5.<init>(r0);
        throw r5;
        r0 = new byte[r1];
        r4.root = r0;
        r5 = r5.publicSeed;
        if (r5 == 0) goto L_0x005d;
        r0 = r5.length;
        if (r0 != r1) goto L_0x0055;
        goto L_0x002a;
        r5 = new java.lang.IllegalArgumentException;
        r0 = "length of publicSeed must be equal to length of digest";
        r5.<init>(r0);
        throw r5;
        r5 = new byte[r1];
        goto L_0x002a;
        return;
        r5 = new java.lang.NullPointerException;
        r0 = "params == null";
        r5.<init>(r0);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pqc.crypto.xmss.XMSSPublicKeyParameters.<init>(org.bouncycastle.pqc.crypto.xmss.XMSSPublicKeyParameters$Builder):void");
    }

    public XMSSParameters getParameters() {
        return this.params;
    }

    public byte[] getPublicSeed() {
        return XMSSUtil.cloneArray(this.publicSeed);
    }

    public byte[] getRoot() {
        return XMSSUtil.cloneArray(this.root);
    }

    public byte[] toByteArray() {
        int digestSize = this.params.getDigestSize();
        byte[] bArr = new byte[(digestSize + digestSize)];
        XMSSUtil.copyBytesAtOffset(bArr, this.root, 0);
        XMSSUtil.copyBytesAtOffset(bArr, this.publicSeed, 0 + digestSize);
        return bArr;
    }
}
