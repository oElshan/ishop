package isha.ishop.entity;

import com.fasterxml.jackson.annotation.JsonView;
import isha.ishop.utils.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientOrder")
public class ClientOrder implements Serializable {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private Client client;
    @JsonView(Views.Public.class)
    private List<OrderItem> orderItems;

    private Date created;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @JsonView(Views.Public.class)
    private Status status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status",foreignKey = @ForeignKey(name = "clientOrder_status__fk"))
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "clientOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "id_client")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOrder clientOrder = (ClientOrder) o;
        return Objects.equals(id, clientOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "id=" + id +
                ", clientPhone=" + client.getPhone() +
                ", orderItems=" + orderItems +
                '}';
    }
}