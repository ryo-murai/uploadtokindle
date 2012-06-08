package org.ry0mry.uploadtokindle.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class UploadUser implements Serializable {
	
	public enum KindleAddrType {
		KINDOLECOM("kindle.com"),
		FREEKINDLECOM("free.kindle.com");
		
		private final String domainAddress;
		private KindleAddrType(String domainAddress) {
			this.domainAddress = domainAddress;
		}
		public String getDomainAddress() {
			return domainAddress;
		}
		public String getActualEmailAddress(String account) {
			return account + "@" + this.domainAddress;
		}
	}

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private User user;
    
    private String uploadDestAddress;
    
    private KindleAddrType addrType;
    
    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUploadDestAddress() {
		return uploadDestAddress;
	}

	public void setUploadDestAddress(String uploadDestAddress) {
		this.uploadDestAddress = uploadDestAddress;
	}

	public KindleAddrType getAddrType() {
		return addrType;
	}

	public void setAddrType(KindleAddrType addrType) {
		this.addrType = addrType;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UploadUser other = (UploadUser) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
