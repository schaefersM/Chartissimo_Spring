import React, { useEffect, useRef, useState } from "react";
import InfiniteScroll from "react-infinite-scroll-component";
import { useAuthStore } from "../../stores/authStore";
import { urlGenerator } from "../../helper";
import GalleryFilter from "./GalleryFilter";
import GalleryItem from "./GalleryItem";

const Gallery = () => {
	const { user } = useAuthStore();
	const [results, setResults] = useState([]);
	const [hasMore, setHasMore] = useState(false);
	const [page, setPage] = useState(0);
	const [locationValues, setLocationValues] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const [isFinished, setIsFinished] = useState(false);
	const [typeValues, setTypeValues] = useState([]);
	const locationValuesList = locationValues
		? locationValues.map(({ value }) => value)
		: [];
	const typeValuesList = typeValues
		? typeValues.map(({ value }) => value)
		: [];
	const urlData = [
		{
			type: "location",
			values: locationValuesList,
		},
		{
			type: "type",
			values: typeValuesList,
		},
	];
	const changeLocationValue = (e) => {
		setLocationValues(e);
		setPage(0);
		setResults([])
		setHasMore(false);
	}

	const changeTypeValue = (e) => {
		setTypeValues(e);
		setPage(0);
		setResults([]);
		setHasMore(false);
	}

	const initialRender = useRef(true);

	const fetchCharts = async () => {
		const { user_id } = user;
		const options = {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
			},
		};

		const url = urlGenerator(
			`http://${process.env.REACT_APP_BACKEND_IP}:${process.env.REACT_APP_API_PORT}/api/user/${user_id}/charts?page=${page}&size=3&`,
			urlData
		);
		const response = await fetch(url, options);
		if (!response.ok) {
			setIsLoading(false);
			setIsFinished(true);
			setHasMore(false);
			setResults([]);
			setPage(0);
		} else {
			const {
				results,
				links: { next },
			} = await response.json();
			setHasMore(next ? true : false);
			setPage((prevState) => (prevState += 1));
			setResults((prevState) => [...prevState, ...results]);
			setIsLoading(false)
			setIsFinished(true);
		}
	};
	useEffect(() => {
		if (initialRender.current) {
			initialRender.current = false;
		} else {
			if (typeValues.length || locationValues.length) {
				try {
					setIsLoading((prevState) => !prevState);
					setResults([]);
					fetchCharts();
				} catch (e) {
					console.log(e);
				}
			} else {
				setIsLoading(false);
				setIsFinished(false);
				setHasMore(false);
				setResults([]);
				setPage(0);
			}
		}
		// eslint-disable-next-line
	}, [typeValues, locationValues, user]);

	const Results = results.length > 0 ? (
		results.map((data) => {
			return (
				<div key={data.id}>
					<GalleryItem data={data} />
				</div>
			);
		})
	) : null;


	return (
		<div className="content-wrapper">
			<div className="d-flex flex-column gallery-container p-3 mb-2">
				<div className="gallery-filter-container d-flex">
					<GalleryFilter
						locationValues={locationValues}
						typeValues={typeValues}
						changeLocationValue={changeLocationValue}
						changeTypeValue={changeTypeValue}
					/>
				</div>
				{isLoading && !results.length && (
					<div className="circle pulse bg-success mx-auto" />
				)}
				<InfiniteScroll
					dataLength={results.length}
					next={fetchCharts}
					hasMore={hasMore}
					loader={<div className="circle pulse bg-success mx-auto" />}
					className="infinite-scroll-component"
				>
					<div className="gallery-filter-results my-2">
						{!isLoading && isFinished && results.length > 0 && Results}
						{!isLoading && isFinished && !results.length ? (
							<h3 className="text-center">
								No Data Available
							</h3>
						) : null}
						{!results.length && !isLoading && !isFinished && (
							<h3 className="text-center">
								Search in your gallery by applying a filter
							</h3>
						)}
					</div>
				</InfiniteScroll>
			</div>
		</div>
	);
};

export default Gallery;
