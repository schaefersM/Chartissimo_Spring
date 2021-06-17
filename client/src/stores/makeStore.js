import React, { useContext, useReducer } from "react";

export default function makeStore(reducer, initialState = {}) {
	const storeContext = React.createContext();
	const dispatchContext = React.createContext();

	const StoreProvider = ({ children }) => {
		const [store, dispatch] = useReducer(reducer, initialState);

		return (
			<dispatchContext.Provider value={dispatch}>
				<storeContext.Provider value={store}>
					{children}
				</storeContext.Provider>
			</dispatchContext.Provider>
		);
	};

	function useStore() {
		return useContext(storeContext);
	}

	function useDispatch() {
		return useContext(dispatchContext);
	}

	return [StoreProvider, useDispatch, useStore];
}
