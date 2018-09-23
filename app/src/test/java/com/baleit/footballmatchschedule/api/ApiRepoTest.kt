package com.baleit.footballmatchschedule.api

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepoTest {
    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepo::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}