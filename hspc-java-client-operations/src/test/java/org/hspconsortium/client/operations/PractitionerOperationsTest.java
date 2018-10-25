package org.hspconsortium.client.operations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hspconsortium.client.operations.model.HSPCClientOperationsSession;
import org.hspconsortium.client.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.uhn.fhir.model.dstu2.resource.Practitioner;
import ca.uhn.fhir.rest.gclient.IRead;
import ca.uhn.fhir.rest.gclient.IReadExecutable;
import ca.uhn.fhir.rest.gclient.IReadTyped;

@RunWith(MockitoJUnitRunner.class)
public class PractitionerOperationsTest {

	@InjectMocks
	private PractitionerOperations testSubject;
	
	@Mock
	private HSPCClientOperationsSession hspcclientOperationsSession;
	
	@Mock
	private Session session;
	
	@Mock
	private IRead read;
	
	@Mock
	private IReadTyped<Practitioner> practitionerIReadTyped;
	
	@Mock
	private IReadExecutable<Practitioner> practitionerIReadExecutable;
	
	private void assertNoMoreInteractions(){
		verifyNoMoreInteractions(hspcclientOperationsSession,session,read,practitionerIReadTyped,practitionerIReadExecutable);
	}
	
	@Test
	public void testPractitionerById(){
		Practitioner o = new Practitioner();
		
		List<Session> sessions = new ArrayList<>();
		sessions.add(session);
		
		when(hspcclientOperationsSession.getFilteredSessions("session#1")).thenReturn(sessions);
		when(session.read()).thenReturn(read);
		when(read.resource(Practitioner.class)).thenReturn(practitionerIReadTyped);
		when(practitionerIReadTyped.withId("12345")).thenReturn(practitionerIReadExecutable);
		when(practitionerIReadExecutable.execute()).thenReturn(o);
		
		List<Practitioner> result = testSubject.getPractitionerForId("12345", "session#1");
		assertEquals(1, result.size());
		assertEquals(o,result.get(0));
		
		verify(hspcclientOperationsSession).getFilteredSessions("session#1");
		verify(session).read();
		verify(read).resource(Practitioner.class);
		verify(practitionerIReadTyped).withId("12345");
		verify(practitionerIReadExecutable).execute();
		
		assertNoMoreInteractions();
	}
}
