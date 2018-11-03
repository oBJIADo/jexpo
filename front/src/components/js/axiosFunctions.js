import Constants from './Constants'

export default {

    pagesRqConfig: function (searchingText, queryIndexes, caseIgnore) {
        const rqConf = (searchingText === undefined || searchingText.length === 0) ?
            {params: {"itemsOnPage": Constants.itemsOnPage}} :
            {
                params: {
                    "itemsOnPage": Constants.itemsOnPage,
                    "param": searchingText === undefined ? null : searchingText,
                    "searchIndexes": queryIndexes === undefined ? null : queryIndexes,
                    "caseIgnore": caseIgnore === undefined ? true : caseIgnore
                }
            };
        const address = (searchingText === undefined || searchingText.length === 0) ?
            Constants.addresses.pageCountUrl : Constants.addresses.pageCountSearchingUrl;

        return {
            rqConf: rqConf,
            address: address
        }
    },

    tasksRqConfig: function (pageNum, searchingText, queryIndexes, caseIgnore) {
        const rqConf = (searchingText === undefined || searchingText.length === 0) ?
            {params: {"num": pageNum, "itemsOnPage": Constants.itemsOnPage}} :
            {
                params: {
                    "num": pageNum,
                    "itemsOnPage": Constants.itemsOnPage,
                    "param": searchingText === undefined ? null : searchingText,
                    "searchIndexes": queryIndexes === undefined ? null : queryIndexes,
                    "caseIgnore": caseIgnore === undefined ? true : caseIgnore
                }
            };
        const address = (searchingText === undefined || searchingText.length === 0) ?
            Constants.addresses.tasksUrl : Constants.addresses.tasksSearchingUrl;

        return {
            rqConf: rqConf,
            address: address
        }
    }

}