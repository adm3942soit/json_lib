package com.json.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.json.helper.JsonHelper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Nikolajs Arhipovs <nikolajs.arhipovs at gmail.com>
 */
@XmlRootElement(name = "data")
public class DataBean {
    public static class TypePayment {

        public Long ID;
        public Short State;
        public Short Mode;
        public String StartDate;
        public String LastDate;
        public String Descriptor;

    }

    public static class TypeRecurring {

        public Long ID;
        public Long OldID;
        public Integer Frequency;
        public String EndDate;

    }

    public static class TypeOrder {

        public String ID;
        public Long Amount;
        public Long DepositAmount;
        public Long ReversalAmount;
        public String Currency;
        public String Description;

    }

    public static class TypeCard {

        public String Token;
        public String Name;
        public String Number;
        public String Expiry;
        public String CSC;

    }

    public static class TypeAddress {

        public String Name;
        public String Address;
        public String City;
        public String Country;
        public String PostIndex;
        public String Phone;
        public String Email;

    }

    public static class Type3D {

        public String XID;
        public String MessageID;
        public String TransactionDate;
        public String Enrolled;
        public String Status;
        public String ECI;
        public TypeACS ACS;
    }

    public static class TypeACS {

        public String URL;
        public String PaReq;
        public String PaRes;

    }

    public static class TypeAuth {

        public String ApprovalCode;
        public String ActionCode;
        public String Description;

    }

    public static class TypeOCfromExistingPayment {

        public Long PaymentID;
        public String OrderID;

    }

    public static class AdditionalData implements Serializable {
        @XmlElement(name = "Sender")
        public String sender;

        public Map<String, String> senderToJsonMap() {
            return JsonHelper.jsonStrToMap(sender);
        }

        @XmlElement(name = "Data")
        public String data;

        public Map<String, String> dataToJsonMap() {
            return JsonHelper.jsonStrToMap(data);
        }

    }

    @XmlElement(name = "AutoDeposit")
    public Boolean AutoDeposit;
    @XmlElement(name = "Payment")
    public TypePayment Payment;
    @XmlElement(name = "Recurring")
    public TypeRecurring Recurring;
    @XmlElement(name = "Order")
    public TypeOrder Order;
    @XmlElement(name = "Card")
    public TypeCard Card;
    @XmlElement(name = "BillingAddress")
    public TypeAddress BillingAddress;
    @XmlElement(name = "DeliveryAddress")
    public TypeAddress DeliveryAddress;
    @XmlElement(name = "D3D")
    public Type3D D3D;
    @XmlElement(name = "Auth")
    public TypeAuth Auth;
    @XmlElement(name = "Notification")
    public String Notification;
    @XmlElement(name = "RemoteAddress")
    public String RemoteAddress;
    @XmlElement(name = "OCfromExistingPayment")
    public TypeOCfromExistingPayment OCfromExistingPayment;

    @XmlElement(name = "Callback")
    public String CallBack;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @XmlElement(name = "AdditionalData")
    public AdditionalData additionalData;

    @XmlTransient
    public String Referrer;

    @Override
    public String toString() {
        return "DataBean{" +
                "AutoDeposit=" + AutoDeposit +
                ", Payment=" + Payment.Mode +
                ", Recurring=" + Recurring +
                ", Order=" + Order.Amount + " " + Order.Currency +
                ", Card=" + Card.Name + " " + Card.Number +
                ", BillingAddress=" + (BillingAddress != null ? BillingAddress.Address : BillingAddress) +
                ", DeliveryAddress=" + (DeliveryAddress != null ? DeliveryAddress.Address : DeliveryAddress) +
                ", D3D=" + D3D +
                ", Auth=" + Auth +
                ", Notification='" + Notification + '\'' +
                ", RemoteAddress='" + RemoteAddress + '\'' +
                ", OCfromExistingPayment=" + OCfromExistingPayment +
                ", CallBack='" + CallBack + '\'' +
                ", additionalData=" + additionalData.sender + " " + additionalData.data +
                ", Referrer='" + Referrer + '\'' +
                '}';
    }
}
