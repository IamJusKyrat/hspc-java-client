package org.hspconsortium.client.operations;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.hspconsortium.client.operations.model.HSPCClientOperationsSession;
import org.hspconsortium.client.session.Session;

import ca.uhn.fhir.model.dstu2.resource.Order;

public class OrdersOperations extends OperationsBase{
	
	public OrdersOperations(HSPCClientOperationsSession session){
		super(session);
	}
	
	public List<Order> getOrderById(String orderId, String... targetSessionKeys){
		
		List<Session> sessions = getSession().getFilteredSessions(targetSessionKeys);
	
		ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<Order>();
	
	    sessions.parallelStream().forEach(s -> orders.add(s.read().resource(Order.class).withId(orderId).execute()));
	
	    return Arrays.asList(orders.toArray(new Order[orders.size()])); 
	}
}
