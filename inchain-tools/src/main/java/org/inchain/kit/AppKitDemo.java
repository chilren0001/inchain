package org.inchain.kit;

import java.io.File;
import java.net.InetSocketAddress;

import org.inchain.Configure;
import org.inchain.account.AccountBody;
import org.inchain.kits.AccountKit;
import org.inchain.kits.AppKit;
import org.inchain.listener.Listener;
import org.inchain.network.NetworkParams;
import org.inchain.network.NodeSeedManager;
import org.inchain.network.Seed;
import org.inchain.network.SeedManager;
import org.inchain.network.TestNetworkParams;

public class AppKitDemo {

	public static void main(String[] args) throws Exception {
		
		SeedManager seedManager = new NodeSeedManager();
		seedManager.add(new Seed(new InetSocketAddress("127.0.0.1", 8322), true, 25000));
		
		NetworkParams network = new TestNetworkParams(seedManager);
		
		//测试前先清空帐户目录
		File dir = new File(Configure.DATA_ACCOUNT);
		if(dir.listFiles() != null) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}
		
		final AppKit kit = new AppKit();
		kit.startSyn();
		
		kit.setInitListener(new Listener() {
			@Override
			public void onComplete() {
				AccountKit accountKit = kit.getAccountKit();
				try {
					Thread.sleep(1000l);
					if(accountKit.getAccountList().isEmpty()) {
						accountKit.createNewCertAccount("123456", "0123456", AccountBody.empty());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
//					accountKit.close();
//					peerKit.stop();
				}
			}
		});
		
	}
}
