package org.hspconsortium.client.operations;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.hspconsortium.client.operations.model.HSPCClientOperationsSession;
import org.hspconsortium.client.session.Session;

import ca.uhn.fhir.model.dstu2.resource.Practitioner;

public class PractitionerOperations extends OperationsBase {

	public PractitionerOperations(HSPCClientOperationsSession session) {
		
		super(session);
	}
	
	public List<Practitioner> getPractitionerForId(String practitionerId, String... targetSessionKeys){
		List<Session> sessions = getSession().getFilteredSessions(targetSessionKeys);
		
		ConcurrentLinkedQueue<Practitioner> practitioner = new ConcurrentLinkedQueue<Practitioner>();
	
	    sessions.parallelStream().forEach(s -> practitioner.add(s.read().resource(Practitioner.class).withId(practitionerId).execute()));
	
	    return Arrays.asList(practitioner.toArray(new Practitioner[practitioner.size()])); 
	}

}
