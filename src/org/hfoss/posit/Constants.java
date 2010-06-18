package org.hfoss.posit;

public class Constants {
	
	public static final int ERR_PASSWORD_MISSING = 0x13;
	public static final int ERR_IMEI_MISSING = 0x14;
	public static final int ERR_EMAIL_INVALID = 0x12;
	public static final int ERR_PASSWORD_UNMATCHED = 0x11;
	public static final int ERR_PASSWORD2_INVALID = 0x10;
	public static final int ERR_PASSWORD1_INVALID = 0x0F;
	public static final int ERR_LASTNAME_MISSING = 0x0E;
	public static final int ERR_FIRSTNAME_MISSING = 0x0D;
	public static final int ERR_EMAIL_MISSING = 0x0C;
	public static final int ERR_AUTHKEY_MISSING = 0x0B;
	public static final int ERR_AUTHKEY_INVALID = 0x0A;
	public static final int REGISTRATION_EMAILEXISTS = 0x09;
	public static final int AUTHN_NOTLOGGEDIN = 0x08;
	public static final int AUTHN_FAILED = 0x07;
	public static final int AUTHN_OK = 0x06;
	public static final int IMAGES_GET_THUMBNAILS = 0x04;
	public static final int IMAGES_GET_FULL = 0x03;
	public static final int PROJECTS_CLOSED = 0x02;
	public static final int PROJECTS_OPEN = 0x01;
	public static final int PROJECTS_ALL = 0x00;
}
/**
   1.
      define(PROJECTS_ALL,                            0x00);
   2.
      define(PROJECTS_OPEN,                           0x01);
   3.
      define(PROJECTS_CLOSED,                         0x02);
   4.
      define(IMAGES_GET_FULL,                         0x03);
   5.
      define(IMAGES_GET_THUMBNAILS,           0x04);
   6.
      define(IMAGES_GET_NONE,                         0x05);
   7.
      define(AUTHN_OK,                                        0x06);
   8.
      define(AUTHN_FAILED,                            0x07);
   9.
      define(AUTHN_NOTLOGGEDIN,                       0x08);
  10.
      define(REGISTRATION_EMAILEXISTS,        0x09);
  11.
      define(ERR_AUTHKEY_INVALID,                     0x0A);
  12.
      define(ERR_AUTHKEY_MISSING,                     0x0B);
  13.
      define(ERR_EMAIL_MISSING,                       0x0C);
  14.
      define(ERR_FIRSTNAME_MISSING,           0x0D);
  15.
      define(ERR_LASTNAME_MISSING,            0x0E);
  16.
      define(ERR_PASSWORD1_INVALID,           0x0F);
  17.
      define(ERR_PASSWORD2_INVALID,           0x10);
  18.
      define(ERR_PASSWORD_UNMATCHED,          0x11);
  19.
      define(ERR_EMAIL_INVALID,                       0x12);
  20.
      define(ERR_PASSWORD_MISSING,            0x13);
  21.
      define(ERR_IMEI_MISSING,                        0x14);

**/